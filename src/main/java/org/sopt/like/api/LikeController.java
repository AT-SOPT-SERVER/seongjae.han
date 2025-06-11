package org.sopt.like.api;

import static org.sopt.global.constants.AppConstants.API_PREFIX;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.global.response.ApiResponse;
import org.sopt.like.api.dto.LikeRequestDto;
import org.sopt.like.api.dto.LikeRequestDto.CreatePostLikeRequest;
import org.sopt.like.api.dto.LikeResponseDto.CreateCommentLikeResponse;
import org.sopt.like.api.dto.LikeResponseDto.CreatePostLikeResponse;
import org.sopt.like.api.dto.LikeResponseDto.DeleteCommentLikeResponse;
import org.sopt.like.api.dto.LikeResponseDto.DeletePostLikeResponse;
import org.sopt.like.application.command.CreateCommentLikeService;
import org.sopt.like.application.command.CreatePostLikeService;
import org.sopt.like.application.command.DeleteCommentLikeService;
import org.sopt.like.application.command.DeletePostLikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_PREFIX + "/likes")
public class LikeController {

  private final CreatePostLikeService createPostLikeService;
  private final DeletePostLikeService deletePostLikeService;
  private final CreateCommentLikeService createCommentLikeService;
  private final DeleteCommentLikeService deleteCommentLikeService;

  @PostMapping("/posts")
  public ResponseEntity<ApiResponse<CreatePostLikeResponse>> createPostLike(
      @RequestHeader("userId") Long userId,
      @RequestBody @Valid CreatePostLikeRequest createPostLikeRequest
  ) {
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(
        CreatePostLikeResponse.from(
            createPostLikeService.execute(userId, createPostLikeRequest.toServiceRequest()))));
  }

  @DeleteMapping("/posts")
  public ResponseEntity<ApiResponse<DeletePostLikeResponse>> deletePostLike(
      @RequestHeader("userId") Long userId,
      @RequestBody @Valid LikeRequestDto.DeletePostLikeRequest createPostLikeRequest
  ) {
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(
        DeletePostLikeResponse.from(
            deletePostLikeService.execute(userId, createPostLikeRequest.toServiceRequest()))));
  }

  @PostMapping("/comments")
  public ResponseEntity<ApiResponse<CreateCommentLikeResponse>> createCommentLike(
      @RequestHeader("userId") Long userId,
      @RequestBody @Valid LikeRequestDto.CreateCommentLikeRequest createCommentLikeRequest
  ) {
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(
        CreateCommentLikeResponse.from(
            createCommentLikeService.execute(userId,
                createCommentLikeRequest.toServiceRequest()))));
  }

  @DeleteMapping("/comments")
  public ResponseEntity<ApiResponse<DeleteCommentLikeResponse>> deleteCommentLike(
      @RequestHeader("userId") Long userId,
      @RequestBody @Valid LikeRequestDto.DeleteCommentLikeRequest deleteCommentLikeRequest
  ) {
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(
        DeleteCommentLikeResponse.from(
            deleteCommentLikeService.execute(userId,
                deleteCommentLikeRequest.toServiceRequest()))));
  }

}
