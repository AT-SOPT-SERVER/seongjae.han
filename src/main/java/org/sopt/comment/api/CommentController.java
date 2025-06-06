package org.sopt.comment.api;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.api.dto.CommentRequestDto;
import org.sopt.comment.api.dto.CommentRequestDto.CommentListRequestDto;
import org.sopt.comment.api.dto.CommentRequestDto.CommentUpdateRequestDto;
import org.sopt.comment.api.dto.CommentResponseDto.CommentItemDto;
import org.sopt.comment.api.dto.CommentResponseDto.CommentListDto;
import org.sopt.comment.application.command.CreateCommentService;
import org.sopt.comment.application.command.UpdateCommentService;
import org.sopt.comment.application.query.GetCommentListService;
import org.sopt.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

  private final CreateCommentService createCommentService;
  private final UpdateCommentService updateCommentService;
  private final GetCommentListService getCommentListService;

  @PostMapping
  public ResponseEntity<ApiResponse<CommentItemDto>> create(
      @RequestBody CommentRequestDto.CommentCreateRequestDto commentCreateRequestDto
  ) {

    return ResponseEntity.ok(ApiResponse.success(CommentItemDto.from(
        createCommentService.execute(commentCreateRequestDto.toServiceRequest()))));
  }

  @PutMapping
  public ResponseEntity<ApiResponse<CommentItemDto>> update(
      @RequestBody CommentUpdateRequestDto commentUpdateRequestDto
  ) {

    return ResponseEntity.ok(
        ApiResponse.success(CommentItemDto.from(
            updateCommentService.execute(commentUpdateRequestDto.toServiceRequest()))));
  }

  @PostMapping("/list")
  public ResponseEntity<ApiResponse<CommentListDto>> list(
      @RequestBody CommentListRequestDto commentListRequestDto
  ) {

    return ResponseEntity.ok(ApiResponse.success(CommentListDto.from(
        getCommentListService.execute(commentListRequestDto.toServiceRequest()))));
  }
}
