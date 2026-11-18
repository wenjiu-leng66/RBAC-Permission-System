package com.municipal.rbac.procedural.config;

import com.municipal.rbac.procedural.error.ApiException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.*;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 统一输出 API、Service、Infrastructure 三层的调用日志。
 *
 * <p>只记录层名、类名、方法名、结果、耗时和异常类型，不记录参数、返回值、密码、Cookie、Token
 * 或员工隐私。请求关联标识由 {@link RequestIdFilter} 写入 MDC，日志 pattern 通过
 * {@code %X{requestId}} 关联三层调用。
 *
 * <p>包级日志级别在 application.yml 中配置，部署时可由环境变量覆盖；业务类不各自散落
 * {@code log.info/debug}，需要新增日志时扩展本切面。
 */
@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class LayerLoggingAspect {

  private static final Logger log = LoggerFactory.getLogger(LayerLoggingAspect.class);

  /** 覆盖成员 4 负责的 API、Service 和 Infrastructure（当前基础设施层为 mapper 包）。 */
  @Pointcut(
      "within(com.municipal.rbac.procedural.controller..*)"
          + " || within(com.municipal.rbac.procedural.service..*)"
          + " || within(com.municipal.rbac.procedural.mapper..*)")
  public void applicationLayers() {}

  @Around("applicationLayers()")
  public Object logLayer(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Class<?> targetClass =
        joinPoint.getTarget() == null
            ? signature.getDeclaringType()
            : AopUtils.getTargetClass(joinPoint.getTarget());
    String layer = layerOf(targetClass);
    String className = targetClass.getSimpleName();
    String methodName = signature.getName();
    long startedAt = System.nanoTime();

    log.trace("layer={} class={} method={} stage=START", layer, className, methodName);
    try {
      Object result = joinPoint.proceed();
      log.debug(
          "layer={} class={} method={} outcome=SUCCESS durationMs={}",
          layer,
          className,
          methodName,
          elapsedMillis(startedAt));
      return result;
    } catch (ApiException e) {
      // 业务拒绝（401/403/404/409 等）是预期结果，记录稳定错误码但不记录消息和参数。
      log.debug(
          "layer={} class={} method={} outcome=REJECTED code={} durationMs={}",
          layer,
          className,
          methodName,
          e.code(),
          elapsedMillis(startedAt));
      throw e;
    } catch (Throwable t) {
      log.warn(
          "layer={} class={} method={} outcome=ERROR exception={} durationMs={}",
          layer,
          className,
          methodName,
          t.getClass().getSimpleName(),
          elapsedMillis(startedAt));
      throw t;
    }
  }

  private static long elapsedMillis(long startedAt) {
    return (System.nanoTime() - startedAt) / 1_000_000L;
  }

  private static String layerOf(Class<?> type) {
    String packageName = type.getPackageName();
    if (packageName.contains(".controller")) {
      return "api";
    }
    if (packageName.contains(".service")) {
      return "service";
    }
    if (packageName.contains(".mapper")) {
      return "infrastructure";
    }
    return "application";
  }
}
