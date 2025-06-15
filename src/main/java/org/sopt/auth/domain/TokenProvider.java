package org.sopt.auth.domain;

public interface TokenProvider {

  String createToken(Long loginId);
}
