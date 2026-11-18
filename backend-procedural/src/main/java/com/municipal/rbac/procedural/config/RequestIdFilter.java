package com.municipal.rbac.procedural.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 为每个请求建立稳定的关联标识，供 API、Service、Infrastructure 三层日志和错误响应共同使用。
 *
 * <p>客户端可以传入 X-Request-Id 以便跨系统追踪；只接受安全字符和受限长度，避免日志注入。
 * 未传或格式不合法时生成新的短标识。请求结束后清理 MDC，避免线程复用串号。
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestIdFilter extends OncePerRequestFilter {

  /** 请求/响应共用的关联标识头。 */
  public static final String HEADER = "X-Request-Id";

  /** 日志 MDC 中的键名，Logback pattern 通过 %X{requestId} 输出。 */
  public static final String MDC_KEY = "requestId";

  private static final Pattern SAFE_REQUEST_ID = Pattern.compile("[A-Za-z0-9._-]{1,64}");

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    String requestId = request.getHeader(HEADER);
    if (requestId == null || !SAFE_REQUEST_ID.matcher(requestId).matches()) {
      requestId = newRequestId();
    }
    MDC.put(MDC_KEY, requestId);
    response.setHeader(HEADER, requestId);
    try {
      chain.doFilter(request, response);
    } finally {
      MDC.remove(MDC_KEY);
    }
  }

  /**
   * 返回当前请求的关联标识；在非请求线程（例如测试或定时任务）中生成一个临时标识。
   * 调用方只把它用于日志和错误响应，不能把它当身份令牌或授权依据。
   */
  public static String currentRequestId() {
    String requestId = MDC.get(MDC_KEY);
    return requestId == null || requestId.isBlank() ? newRequestId() : requestId;
  }

  private static String newRequestId() {
    return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
  }
}
