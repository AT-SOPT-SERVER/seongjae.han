package org.sopt.comment.api;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.application.command.CreateCommentService;
import org.sopt.comment.dto.CommentRequestDto.CommentCreateRequestDto;
import org.sopt.comment.dto.CommentResponseDto.CommentItemDto;
import org.sopt.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

  private final CreateCommentService createCommentService;

  @PostMapping
  public ResponseEntity<ApiResponse<CommentItemDto>> create(
      @RequestBody CommentCreateRequestDto commentCreateRequestDto
  ) {

    return ResponseEntity.ok(
        ApiResponse.success(createCommentService.execute(commentCreateRequestDto)));
  }
}
