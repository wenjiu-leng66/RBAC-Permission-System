/*
 * RBAC0 presentation policy: protects navigation and controls UI visibility.
 * The backend must authenticate and authorize every protected API request.
 * This file is a classic browser script and a dependency-free CommonJS module.
 */
(function (root, createPolicy) {
  'use strict';
  var policy = createPolicy();
  if (typeof module === 'object' && module.exports) {
    module.exports = policy;
  } else {
    root.Rbac0 = policy;
  }
})(typeof globalThis !== 'undefined' ? globalThis : this, function () {
  'use strict';

  function isCode(value) {
    return typeof value === 'string' && value.length > 0 && value.trim() === value;
  }

  function readySession(session) {
    return Boolean(session && session.authenticated === true &&
      session.permissionsLoaded === true && Array.isArray(session.permissions));
  }

  function hasPermission(session, code) {
    return readySession(session) && isCode(code) && session.permissions.includes(code);
  }

  function validRoute(route) {
    return Boolean(route && typeof route === 'object' && !Array.isArray(route) &&
      typeof route.path === 'string' && /^\/(?!\/)\S*$/.test(route.path) &&
      (route.public === undefined || typeof route.public === 'boolean'));
  }

  function denied(redirect, reason) {
    return { allowed: false, redirect: redirect, reason: reason };
  }

  function canVisit(session, route) {
    var knownRoute = validRoute(route);
    if (knownRoute && route.public === true) {
      return { allowed: true, redirect: null, reason: 'public' };
    }
    if (!session || session.authenticated !== true) {
      return denied('/login', 'unauthenticated');
    }
    if (!knownRoute) {
      return denied('/403', 'invalid-route');
    }
    if (!readySession(session)) {
      return denied('/403', 'permissions-not-loaded');
    }
    if (!Array.isArray(route.requiredPermissions) || route.requiredPermissions.length === 0) {
      return denied('/403', 'missing-permission-rule');
    }
    if (!Array.from(route.requiredPermissions).every(isCode) ||
        (route.match !== undefined && route.match !== 'all' && route.match !== 'any')) {
      return denied('/403', 'invalid-permission-rule');
    }
    var granted = route.match === 'any'
      ? route.requiredPermissions.some(function (code) { return hasPermission(session, code); })
      : route.requiredPermissions.every(function (code) { return hasPermission(session, code); });
    return granted
      ? { allowed: true, redirect: null, reason: 'authorized' }
      : denied('/403', 'forbidden');
  }

  function visibleRoutes(session, routes) {
    if (!Array.isArray(routes)) return [];
    return routes.filter(function (route) { return canVisit(session, route).allowed; });
  }

  function effectivePermissionCodes(user, roles) {
    if (!user || !Array.isArray(user.roleIds) || !Array.isArray(roles)) return [];
    var assigned = new Set(user.roleIds.filter(isCode));
    var permissions = new Set();
    roles.forEach(function (role) {
      if (!role || !isCode(role.id) || !assigned.has(role.id) ||
          (role.enabled !== undefined && role.enabled !== true) ||
          !Array.isArray(role.permissionCodes)) return;
      role.permissionCodes.filter(isCode).forEach(function (code) { permissions.add(code); });
    });
    return Array.from(permissions).sort();
  }

  return Object.freeze({
    hasPermission: hasPermission,
    canVisit: canVisit,
    visibleRoutes: visibleRoutes,
    effectivePermissionCodes: effectivePermissionCodes
  });
});
