package org.sopt.comment.application.command;

import org.sopt.comment.dto.CommentRequestDto.CommentDeleteRequestDto;

public interface DeleteCommentService {

  void execute(CommentDeleteRequestDto commentDeleteRequestDto);
}
