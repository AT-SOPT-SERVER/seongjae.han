package org.sopt.global.util;

import static org.sopt.global.constants.AppConstants.DEFAULT_PAGE_SIZE;

public class PaginationUtils {

  public static int correctPage(Integer page) {
    return (page == null || page < 0) ? 0 : page;
  }

  public static int correctSize(Integer size) {
    return (size == null || size < 0) ? DEFAULT_PAGE_SIZE : size;
  }
}
