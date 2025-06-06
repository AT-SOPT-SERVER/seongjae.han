package org.sopt.post.api;

import lombok.RequiredArgsConstructor;
import org.sopt.post.application.PostApiService;
import org.sopt.post.application.command.CreatePostService;
import org.sopt.post.application.command.DeletePostService;
import org.sopt.post.application.command.UpdatePostService;
import org.sopt.post.application.query.GetPostByIdService;
import org.sopt.post.dto.PostServiceRequestDto.CreatePostServiceRequest;
import org.sopt.post.dto.PostServiceRequestDto.UpdatePostServiceRequest;
import org.sopt.global.response.ApiResponse;
import org.sopt.post.dto.PostServiceResponseDto.PostListServiceResponse;
import org.sopt.post.dto.PostServiceResponseDto.PostItemServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

  private final CreatePostService createPostService;
  private final UpdatePostService updatePostService;
  private final GetPostByIdService getPostByIdService;
  private final PostApiService postApiService;
  private final DeletePostService deletePostService;

  @PostMapping
  public ResponseEntity<ApiResponse<PostItemServiceResponse>> createPost(
      @RequestHeader("userId") Long userId,
      @RequestBody final CreatePostServiceRequest createRequest) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(createPostService.execute(userId, createRequest)));
  }

  @PutMapping
  public ResponseEntity<ApiResponse<PostItemServiceResponse>> updatePostTitle(
      @RequestHeader(value = "userId") Long userId,
      @RequestBody final UpdatePostServiceRequest updateRequest) {

    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(
        updatePostService.execute(userId, updateRequest)));
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<ApiResponse<Object>> deletePostById(
      @RequestHeader(value = "userId") Long userId,
      @PathVariable("postId") final Long postId) {

    deletePostService.execute(userId, postId);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success());
  }

  @GetMapping("/search")
  public ResponseEntity<ApiResponse<PostListServiceResponse>> searchPostsByKeyword(
      @RequestHeader(value = "userId") String userId,
      @RequestParam(value = "keyword") final String keyword,
      @RequestParam(value = "searchSort", defaultValue = "POST_TITLE") final String searchSort
  ) {
    PostSearchSort postSearchSort = PostSearchSort.from(searchSort);

    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(
            postApiService.searchPostsByKeyword(postSearchSort, keyword)));
  }

  @GetMapping("/{postId}")
  public ResponseEntity<ApiResponse<PostItemServiceResponse>> getPostById(
      @RequestHeader(value = "userId") Long userId,
      @PathVariable("postId") final Long postId) {

    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(getPostByIdService.execute(userId, postId)));
  }
}
