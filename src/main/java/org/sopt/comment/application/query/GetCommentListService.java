package org.sopt.comment.application.query;

import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentListServiceRequestDto;
import org.sopt.comment.application.dto.CommentServiceResponseDto.CommentListDto;

public interface GetCommentListService {

  public CommentListDto execute(CommentListServiceRequestDto commentListRequestDto);
}
