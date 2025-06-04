package org.sopt.comment.application.query;

import org.sopt.comment.dto.CommentRequestDto.CommentListRequestDto;
import org.sopt.comment.dto.CommentResponseDto.CommentListDto;

public interface GetCommentListService {

  public CommentListDto execute(CommentListRequestDto commentListRequestDto);
}
