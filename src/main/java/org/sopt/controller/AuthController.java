package org.sopt.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
