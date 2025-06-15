package org.sopt.auth.infrastructure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.sopt.auth.domain.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider implements TokenProvider {

  @Value("${jwt.secret}")
  private String secret;
  private final long expirationMileSeconds = 60 * 60 * 1000; // 1시간

  @Override
  public String createToken(final Long loginId) {
    return Jwts.builder()
        .setSubject(loginId.toString())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expirationMileSeconds))
        .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
        .compact();
  }

  public Claims parseToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(secret.getBytes())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public Long getUserId(String token) {
    return Long.valueOf(parseToken(token).getSubject());
  }

  public String getUserRole(String token) {
    return parseToken(token).get("role", String.class);
  }
}
