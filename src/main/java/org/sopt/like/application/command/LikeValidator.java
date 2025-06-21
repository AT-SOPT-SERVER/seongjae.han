package org.sopt.like.application.command;

import org.sopt.user.domain.User;

public interface LikeValidator {

  void validatePostLikeDuplicate(User user, Long postId);

  void validatePostExists(final Long postId);
}
