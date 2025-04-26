package org.sopt.util;


public interface LoggingUtil {

  public void info(String message, Object... data);

  public void error(Exception exception);
}
