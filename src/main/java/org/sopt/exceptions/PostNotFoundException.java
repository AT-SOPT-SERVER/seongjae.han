package org.sopt.exceptions;

public class PostNotFoundException extends RuntimeException{

  public PostNotFoundException() {
    super("존재하지 않는 게시물입니다.");
  }
}
