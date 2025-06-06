package org.sopt.comment.application.command;

import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentDeleteServiceRequestDto;

public interface DeleteCommentService {

  void execute(CommentDeleteServiceRequestDto commentDeleteRequestDto);
}
