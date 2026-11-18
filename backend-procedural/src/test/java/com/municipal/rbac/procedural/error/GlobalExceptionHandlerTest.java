package com.municipal.rbac.procedural.error;

import static org.junit.jupiter.api.Assertions.*;

import com.municipal.rbac.procedural.config.RequestIdFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

/** 错误响应必须复用请求入口的关联标识，便于把 4xx/5xx 与三层日志串起来。 */
class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

  @AfterEach
  void clearMdc() {
    MDC.clear();
  }

  @Test
  void errorBodyUsesTheCurrentRequestId() {
    MDC.put(RequestIdFilter.MDC_KEY, "check-request-01");

    var response = handler.api(ApiException.badRequest("VALIDATION", "bad input"));

    assertEquals("check-request-01", response.getBody().error().requestId());
    assertEquals("VALIDATION", response.getBody().error().code());
  }

  @Test
  void errorBodyFallsBackToASafeIdOutsideARequestThread() {
    MDC.clear();

    var response = handler.api(ApiException.unauthorized("UNAUTHENTICATED", "login first"));

    assertNotNull(response.getBody().error().requestId());
    assertTrue(response.getBody().error().requestId().matches("[A-Za-z0-9._-]{1,64}"));
  }
}
