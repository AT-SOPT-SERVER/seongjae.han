package org.sopt.util;

public class DomainIdUtil {

  private Integer id = 0;

  public Integer generateId() {
    return id++;
  }
}
