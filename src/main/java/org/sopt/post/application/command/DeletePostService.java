package org.sopt.post.application.command;

public interface DeletePostService {

  public void execute(final Long userId, final Long postId);
}
