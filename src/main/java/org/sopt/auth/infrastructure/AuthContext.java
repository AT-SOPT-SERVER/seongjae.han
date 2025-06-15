package org.sopt.auth.infrastructure;

public class AuthContext {
  private static final ThreadLocal<Long> currentUserId = new ThreadLocal<>();

  public static void setCurrentUserId(Long userId) {
    currentUserId.set(userId);
  }

  public static Long getCurrentUserId() {
    return currentUserId.get();
  }

  public static void clear() {
    currentUserId.remove();
  }
}
