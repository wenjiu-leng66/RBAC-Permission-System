package com.municipal.rbac.procedural.controller;

import com.municipal.rbac.procedural.dto.Requests;
import com.municipal.rbac.procedural.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 角色管理 HTTP 适配层；PUT permissions 是带 version 的全集合替换，不是增量添加权限。
 */
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
  private final RoleService roles;

  public RoleController(RoleService roles) {
    this.roles = roles;
  }

  @Operation(operationId = "get_roles")
  @GetMapping
  public Map<String, Object> list(
      @RequestParam(required = false) String keyword,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "20") int pageSize,
      HttpSession session) {
    return roles.list(session, keyword, page, pageSize);
  }

  @Operation(operationId = "get_roles_id")
  @GetMapping("/{id}")
  public Map<String, Object> detail(@PathVariable String id, HttpSession session) {
    return roles.detail(session, id);
  }

  @Operation(operationId = "post_roles")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Map<String, Object> create(
      @Valid @RequestBody Requests.CreateRole body, HttpSession session) {
    return roles.create(session, body);
  }

  @Operation(operationId = "patch_roles_id")
  @PatchMapping("/{id}")
  public Map<String, Object> update(
      @PathVariable String id, @Valid @RequestBody Requests.UpdateRole body, HttpSession session) {
    return roles.update(session, id, body);
  }

  @Operation(operationId = "patch_roles_id_status")
  @PatchMapping("/{id}/status")
  public Map<String, Object> status(
      @PathVariable String id, @Valid @RequestBody Requests.Enabled body, HttpSession session) {
    return roles.status(session, id, body);
  }

  @Operation(operationId = "delete_roles_id")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
      @PathVariable String id,
      @RequestParam(defaultValue = "false") boolean confirm,
      HttpSession session) {
    roles.delete(session, id, confirm);
  }

  @Operation(operationId = "get_roles_id_users")
  @GetMapping("/{id}/users")
  public Map<String, Object> users(
      @PathVariable String id,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "20") int pageSize,
      HttpSession session) {
    return roles.roleUsers(session, id, page, pageSize);
  }

  @Operation(operationId = "get_roles_id_permissions")
  @GetMapping("/{id}/permissions")
  public Map<String, Object> permissions(@PathVariable String id, HttpSession session) {
    return roles.rolePermissions(session, id);
  }

  @Operation(operationId = "put_roles_id_permissions")
  @PutMapping("/{id}/permissions")
  public Map<String, Object> replace(
      @PathVariable String id, @Valid @RequestBody Requests.Grants body, HttpSession session) {
    return roles.replacePermissions(session, id, body);
  }
}
