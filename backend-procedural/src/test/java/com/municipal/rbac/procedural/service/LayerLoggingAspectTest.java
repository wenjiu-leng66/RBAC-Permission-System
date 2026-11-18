package com.municipal.rbac.procedural.service;

import static org.junit.jupiter.api.Assertions.*;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.municipal.rbac.procedural.config.LayerLoggingAspect;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

/** 三层日志切面：记录层/类/方法/结果/耗时/异常类型，不记录参数或返回内容。 */
class LayerLoggingAspectTest {

  private Logger aspectLogger;
  private Level previousLevel;
  private ListAppender<ILoggingEvent> appender;
  private LoggingProbe proxy;

  @BeforeEach
  void setUp() {
    aspectLogger = (Logger) LoggerFactory.getLogger(LayerLoggingAspect.class);
    previousLevel = aspectLogger.getLevel();
    appender = new ListAppender<>();
    appender.start();
    aspectLogger.addAppender(appender);
    aspectLogger.setLevel(Level.DEBUG);

    AspectJProxyFactory factory = new AspectJProxyFactory(new LoggingProbe());
    factory.addAspect(new LayerLoggingAspect());
    proxy = factory.getProxy();
  }

  @AfterEach
  void tearDown() {
    aspectLogger.detachAppender(appender);
    aspectLogger.setLevel(previousLevel);
    appender.stop();
  }

  @Test
  void logsLayerClassMethodOutcomeAndDurationWithoutArguments() {
    assertEquals("return-value-secret", proxy.echo("argument-secret"));

    String messages = messages();
    assertTrue(messages.contains("layer=service"), messages);
    assertTrue(messages.contains("class=LoggingProbe"), messages);
    assertTrue(messages.contains("method=echo"), messages);
    assertTrue(messages.contains("outcome=SUCCESS"), messages);
    assertTrue(messages.contains("durationMs="), messages);
    assertFalse(messages.contains("argument-secret"), messages);
    assertFalse(messages.contains("return-value-secret"), messages);
  }

  @Test
  void logsExceptionTypeWithoutExceptionMessage() {
    assertThrows(IllegalStateException.class, () -> proxy.fail("argument-secret"));

    String messages = messages();
    assertTrue(messages.contains("outcome=ERROR"), messages);
    assertTrue(messages.contains("exception=IllegalStateException"), messages);
    assertFalse(messages.contains("argument-secret"), messages);
    assertFalse(messages.contains("exception-message-secret"), messages);
  }

  private String messages() {
    return appender.list.stream()
        .map(ILoggingEvent::getFormattedMessage)
        .collect(Collectors.joining("\n"));
  }

  /** 放在 service 包下，确保切点 within(...service..*) 能匹配。 */
  public static class LoggingProbe {
    public String echo(String value) {
      return "return-value-secret";
    }

    public void fail(String value) {
      throw new IllegalStateException("exception-message-secret");
    }
  }
}
