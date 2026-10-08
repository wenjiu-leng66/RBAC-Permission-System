// 保留成员 3 的经典脚本；浏览器加载后通过 Rbac0 使用同一份策略。
import './authorization/rbac0-policy.js'

const policy = globalThis.Rbac0
if (!policy) throw new Error('RBAC0 权限策略未加载')

export function policySession(auth) {
  return {
    authenticated: auth.isAuthenticated,
    permissionsLoaded: auth.permissionsLoaded,
    userId: auth.user?.id,
    permissions: auth.permissions
  }
}

export function routeAccess(auth, to) {
  const session = policySession(auth)
  // 错误页面仅要求登录，避免 policy 拒绝后重复跳转到自身。
  if (to.meta.sessionOnly) {
    return session.authenticated
      ? { allowed: true, redirect: null, reason: 'session-only' }
      : { allowed: false, redirect: '/login', reason: 'unauthenticated' }
  }
  return policy.canVisit(session, {
    path: to.path,
    public: to.meta.public,
    requiredPermissions: to.meta.permission ? [to.meta.permission] : undefined
  })
}

export function visibleNavigation(auth, items) {
  return policy.visibleRoutes(policySession(auth), items.map(item => ({
    ...item, requiredPermissions: [item.permission]
  })))
}
