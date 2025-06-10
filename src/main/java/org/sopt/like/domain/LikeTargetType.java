package org.sopt.like.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikeTargetType {

  POST("게시물"), COMMENT("댓글");

  private final String description;

}
