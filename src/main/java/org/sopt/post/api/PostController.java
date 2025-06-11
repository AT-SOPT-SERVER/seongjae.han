package org.sopt.post.api;

import static org.sopt.global.constants.AppConstants.API_PREFIX;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.post.api.dto.PostRequestDto.CreatePostRequest;
import org.sopt.post.api.dto.PostRequestDto.SearchPostListRequest;
import org.sopt.post.api.dto.PostRequestDto.UpdatePostRequest;
import org.sopt.post.api.dto.PostResponseDto.PostItemResponse;
import org.sopt.post.api.dto.PostResponseDto.PostListResponse;
import org.sopt.post.application.command.CreatePostService;
import org.sopt.post.application.command.DeletePostService;
import org.sopt.post.application.command.UpdatePostService;
import org.sopt.post.application.dto.PostServiceRequestDto.GetAllPostsServiceRequest;
import org.sopt.post.application.query.GetAllPostsService;
import org.sopt.post.application.query.GetPostByIdService;
import org.sopt.post.application.query.SearchPostsService;
import org.sopt.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_PREFIX + "/posts")
public class PostController {

  private final CreatePostService createPostService;
  private final UpdatePostService updatePostService;
  private final GetPostByIdService getPostByIdService;
  private final DeletePostService deletePostService;
  private final GetAllPostsService getAllPostsService;
  private final SearchPostsService searchPostsService;

  @PostMapping
  public ResponseEntity<ApiResponse<PostItemResponse>> createPost(
      @RequestHeader("userId") Long userId,
      @Valid @RequestBody final CreatePostRequest createRequest) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(PostItemResponse.from(
            createPostService.execute(userId, createRequest.toServiceRequest()))));
  }

  @PutMapping
  public ResponseEntity<ApiResponse<PostItemResponse>> updatePostTitle(
      @RequestHeader(value = "userId") Long userId,
      @Valid @RequestBody final UpdatePostRequest updateRequest) {

    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(
        PostItemResponse.from(
            updatePostService.execute(userId, updateRequest.toServiceRequest()))));
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<ApiResponse<Void>> deletePostById(
      @RequestHeader(value = "userId") Long userId,
      @PathVariable("postId") final Long postId) {

    deletePostService.execute(userId, postId);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success());
  }

  @GetMapping
  public ResponseEntity<ApiResponse<PostListResponse>> getAllPosts(
      @RequestHeader(value = "userId") Long userId,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size,
      @RequestParam(value = "sort", required = false) String sort
  ) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(
            PostListResponse.from(getAllPostsService.execute(userId,
                GetAllPostsServiceRequest.of(page, size, sort)))));
  }

  @PostMapping("/search")
  public ResponseEntity<ApiResponse<PostListResponse>> searchPostsByKeyword(
      @RequestHeader(value = "userId") Long userId,
      @Valid @RequestBody SearchPostListRequest searchPostListRequest
  ) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(
            PostListResponse.from(
                searchPostsService.execute(userId, searchPostListRequest.toServiceRequest()))));
  }

  @GetMapping("/{postId}")
  public ResponseEntity<ApiResponse<PostItemResponse>> getPostById(
      @RequestHeader(value = "userId") Long userId,
      @PathVariable("postId") final Long postId) {

    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ApiResponse.success(PostItemResponse.from(getPostByIdService.execute(userId, postId))));
  }
}
