package org.sopt.api.controller.comment;

import org.sopt.api.service.comment.CommentApiService;
import org.sopt.api.service.comment.dto.CommentRequestDto.CommentCreateRequestDto;
import org.sopt.api.service.comment.dto.CommentResponseDto.CommentItemDto;
import org.sopt.global.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {

  private final CommentApiService commentApiService;

  public CommentController(final CommentApiService commentApiService) {
    this.commentApiService = commentApiService;
  }

  @PostMapping
  public ResponseEntity<ApiResponse<CommentItemDto>> create(
      @RequestBody CommentCreateRequestDto commentCreateRequestDto
  ) {
    return ResponseEntity.ok(ApiResponse.success(commentApiService.createComment(commentCreateRequestDto)));
  }
}
