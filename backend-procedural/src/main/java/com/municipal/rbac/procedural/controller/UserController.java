package com.municipal.rbac.procedural.controller;

import com.municipal.rbac.procedural.dto.Requests;
import com.municipal.rbac.procedural.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理 HTTP 适配层；基础资料、状态、重置密码和角色分配分开调用，避免一个请求跨越多种权限边界。
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
  private final UserService users;

  public UserController(UserService users) {
    this.users = users;
  }

  @Operation(operationId = "get_users")
  @GetMapping
  public Map<String, Object> list(
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) String departmentId,
      @RequestParam(required = false) String roleId,
      @RequestParam(required = false) String username,
      @RequestParam(required = false) String name,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "20") int pageSize,
      HttpSession session) {
    return users.list(session, keyword, departmentId, roleId, username, name, page, pageSize);
  }

  @Operation(operationId = "get_users_id")
  @GetMapping("/{id}")
  public Map<String, Object> detail(@PathVariable String id, HttpSession session) {
    return users.detail(session, id);
  }

  @Operation(operationId = "post_users")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Map<String, Object> create(
      @Valid @RequestBody Requests.CreateUser body, HttpSession session) {
    return users.create(session, body);
  }

  @Operation(operationId = "put_users_id")
  @PutMapping("/{id}")
  public Map<String, Object> update(
      @PathVariable String id, @Valid @RequestBody Requests.UpdateUser body, HttpSession session) {
    return users.update(session, id, body);
  }

  @Operation(operationId = "patch_users_id_status")
  @PatchMapping("/{id}/status")
  public Map<String, Object> status(
      @PathVariable String id, @Valid @RequestBody Requests.Status body, HttpSession session) {
    return users.status(session, id, body);
  }

  @Operation(operationId = "delete_users_id")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable String id, HttpSession session) {
    users.delete(session, id);
  }

  @Operation(operationId = "post_users_id_password_reset")
  @PostMapping("/{id}/password-reset")
  public Map<String, Object> reset(@PathVariable String id, HttpSession session) {
    users.reset(session, id);
    return Map.of("success", true);
  }

  @Operation(operationId = "get_users_id_roles")
  @GetMapping("/{id}/roles")
  public Map<String, Object> roles(@PathVariable String id, HttpSession session) {
    return users.userRoles(session, id);
  }

  @Operation(operationId = "put_users_id_roles")
  @PutMapping("/{id}/roles")
  public Map<String, Object> replaceRoles(
      @PathVariable String id, @Valid @RequestBody Requests.Roles body, HttpSession session) {
    return users.replaceRoles(session, id, body);
  }
}
