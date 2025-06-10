package org.sopt.comment.application.query;

import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentListServiceRequestDto;
import org.sopt.comment.application.dto.CommentServiceResponseDto.CommentPageListDto;

public interface GetCommentListService {

  public CommentPageListDto execute(CommentListServiceRequestDto commentListRequestDto);
}
