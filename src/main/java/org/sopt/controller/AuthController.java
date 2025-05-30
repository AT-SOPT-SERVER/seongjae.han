package org.sopt.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.sopt.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  @GetMapping("/login")
  public ResponseEntity<String> login(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Basic ")) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("헤더가 이상합니다요.");
    }

    String credentials = getCredentialsFromHeader(authHeader);
    final boolean isUser = authorizeUser(credentials);

    if (isUser) {
      return ResponseEntity.ok("인증성공");
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
  }

  @GetMapping("/set-cookie")
  public ResponseEntity<String> setCookie(
      HttpServletRequest request,
      HttpServletResponse response
  ) {
    final String userName = "userSopt";
    final String password = "sopt1234";

    final Cookie userNameCookie = new Cookie("userId", userName);
    final Cookie passwordCookie = new Cookie("password", password);

    userNameCookie.setPath("/");
    passwordCookie.setPath("/");

    response.addCookie(userNameCookie);
    response.addCookie(passwordCookie);

    return ResponseEntity.ok("쿠키가 맛있게 구워졌습니다.");
  }

  @GetMapping("/get-cookie")
  public ResponseEntity<String> getCookie(
      @CookieValue("userId") String userId,
      @CookieValue("password") String password
  ) {
    return ResponseEntity.ok("받은 쿠키 유저 아이디: " + userId + " 유저 비밀번호: " + password);
  }

  @PostMapping("/login")
  public ResponseEntity<String> loginV2(HttpServletRequest request) {
    String userId = "userSopt";
    String password = "sopt1234";

    if (userId.equals("userSopt") && password.equals("sopt1234")) {
      final HttpSession session = request.getSession(true);
      session.setAttribute("user", User.of("soptUser", "메일"));

      return ResponseEntity.ok("세션 저장 완료");
    }

    throw new RuntimeException("로그인 실패");
  }

  private static boolean authorizeUser(final String credentials) {
    final String[] parts = credentials.split(":");
    final String userName = parts[0];
    final String password = parts[1];

    return userName.equals("soptUser") && password.equals("sopt1234");
  }

  private static String getCredentialsFromHeader(final String authHeader) {
    final String decodedString = authHeader.substring("Basic ".length());
    byte[] decodedByte = Base64.getDecoder().decode(decodedString);

    return new String(decodedByte, StandardCharsets.UTF_8);
  }
}
