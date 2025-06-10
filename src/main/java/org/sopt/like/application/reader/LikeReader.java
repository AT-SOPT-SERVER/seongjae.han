package org.sopt.like.application.reader;

import org.sopt.user.domain.User;

public interface LikeReader {

  boolean existPostLike(User user, Long postId);
}
