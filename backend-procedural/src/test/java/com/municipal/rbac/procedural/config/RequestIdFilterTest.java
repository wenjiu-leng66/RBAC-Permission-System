package com.municipal.rbac.procedural.config;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.servlet.FilterChain;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.mock.web.*;

/** 请求关联标识：透传合法值、拒绝非法值、响应头与 MDC 一致、请求结束后清理。 */
class RequestIdFilterTest {

  private final RequestIdFilter filter = new RequestIdFilter();

  @AfterEach
  void clearMdc() {
    MDC.clear();
  }

  @Test
  void generatesSafeRequestIdWhenHeaderIsMissing() throws Exception {
    var request = new MockHttpServletRequest("GET", "/api/v1/health");
    var response = new MockHttpServletResponse();
    AtomicReference<String> seenInChain = new AtomicReference<>();
    FilterChain chain = (req, res) -> seenInChain.set(MDC.get(RequestIdFilter.MDC_KEY));

    filter.doFilter(request, response, chain);

    String generated = response.getHeader(RequestIdFilter.HEADER);
    assertNotNull(generated);
    assertTrue(generated.matches("[A-Za-z0-9._-]{1,64}"));
    assertEquals(generated, seenInChain.get());
    assertNull(MDC.get(RequestIdFilter.MDC_KEY));
  }

  @Test
  void propagatesClientRequestIdWhenItIsSafe() throws Exception {
    var request = new MockHttpServletRequest("GET", "/api/v1/health");
    request.addHeader(RequestIdFilter.HEADER, "check-2026-11-12.01");
    var response = new MockHttpServletResponse();
    AtomicReference<String> seenInChain = new AtomicReference<>();
    FilterChain chain = (req, res) -> seenInChain.set(MDC.get(RequestIdFilter.MDC_KEY));

    filter.doFilter(request, response, chain);

    assertEquals("check-2026-11-12.01", response.getHeader(RequestIdFilter.HEADER));
    assertEquals("check-2026-11-12.01", seenInChain.get());
  }

  @Test
  void replacesUnsafeClientRequestIdToPreventLogInjection() throws Exception {
    var request = new MockHttpServletRequest("GET", "/api/v1/health");
    request.addHeader(RequestIdFilter.HEADER, "bad\r\nvalue");
    var response = new MockHttpServletResponse();

    filter.doFilter(request, response, (req, res) -> {});

    String generated = response.getHeader(RequestIdFilter.HEADER);
    assertNotNull(generated);
    assertNotEquals("bad\r\nvalue", generated);
    assertTrue(generated.matches("[A-Za-z0-9._-]{1,64}"));
  }

  @Test
  void currentRequestIdFallsBackOutsideARequestThread() {
    MDC.clear();
    String fallback = RequestIdFilter.currentRequestId();
    assertNotNull(fallback);
    assertTrue(fallback.matches("[A-Za-z0-9._-]{1,64}"));
  }
}
