package com.municipal.rbac.procedural.controller;

import com.municipal.rbac.procedural.service.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import java.util.*;
import org.springframework.web.bind.annotation.*;

/**
 * 门户元数据及一期员工台账入口。元数据的按钮 enabled 仅用于展示，写接口仍独立检查动作/范围。
 */
@RestController
@RequestMapping("/api/v1")
public class PortalController {
  private final PortalService portal;
  private final LedgerService ledger;

  public PortalController(PortalService portal, LedgerService ledger) {
    this.portal = portal;
    this.ledger = ledger;
  }

  @Operation(operationId = "get_portal_menu")
  @GetMapping("/portal/menu")
  public List<Map<String, Object>> menu(HttpSession session) {
    return portal.menu(session);
  }

  @Operation(operationId = "get_portal_functions_functionCode")
  @GetMapping("/portal/functions/{functionCode}")
  public Map<String, Object> function(@PathVariable String functionCode, HttpSession session) {
    return portal.function(session, functionCode);
  }

  @Operation(operationId = "get_ledger_employees")
  @GetMapping("/ledger/employees")
  public Map<String, Object> employees(
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) String departmentId,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "20") int pageSize,
      HttpSession session) {
    return ledger.list(session, keyword, departmentId, page, pageSize);
  }

  @Operation(operationId = "patch_ledger_employees_id")
  @PatchMapping("/ledger/employees/{id}")
  public Map<String, Object> update(
      @PathVariable String id, @RequestBody Map<String, Object> body, HttpSession session) {
    return ledger.update(session, id, body);
  }
}
