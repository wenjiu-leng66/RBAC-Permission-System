package com.municipal.rbac.procedural.controller;

import static org.junit.jupiter.api.Assertions.*;

import io.swagger.v3.oas.annotations.Operation;
import java.lang.reflect.Method;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 稳定 operationId 是 API 契约、权限—API 映射和 Controller 三者的共同键。
 * 本测试防止新增控制器方法时漏标、空标或重名；数量与当前 OpenAPI 0.3.0 的 33 个操作一致。
 */
class ControllerOperationIdTest {

  private static final List<Class<?>> CONTROLLERS =
      List.of(
          AuthController.class,
          CatalogController.class,
          DepartmentController.class,
          HealthController.class,
          MeController.class,
          PortalController.class,
          RoleController.class,
          UserController.class);

  @Test
  void everyRequestMappedControllerMethodHasAUniqueOperationId() {
    Set<String> operationIds = new HashSet<>();
    List<String> problems = new ArrayList<>();

    for (Class<?> controller : CONTROLLERS) {
      for (Method method : controller.getDeclaredMethods()) {
        if (method.isSynthetic() || method.isBridge()) {
          continue;
        }
        RequestMapping mapping =
            AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
        if (mapping == null) {
          continue;
        }
        Operation operation = method.getAnnotation(Operation.class);
        if (operation == null || operation.operationId().isBlank()) {
          problems.add(controller.getSimpleName() + "#" + method.getName() + " 缺 operationId");
        } else if (!operationIds.add(operation.operationId())) {
          problems.add("operationId 重复: " + operation.operationId());
        }
      }
    }

    assertTrue(problems.isEmpty(), String.join("\n", problems));
    assertEquals(33, operationIds.size(), "Controller operationId 数量应与 OpenAPI 的 33 个操作一致");
  }
}
