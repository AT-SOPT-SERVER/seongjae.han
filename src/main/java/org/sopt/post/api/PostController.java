package org.sopt.post.api;

import org.sopt.post.dto.PostRequestDto.CreateRequest;
import org.sopt.post.dto.PostRequestDto.UpdateRequest;
import org.sopt.post.dto.PostResponseDto;
import org.sopt.global.response.ApiResponse;
import org.sopt.post.application.PostApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

  private final PostApiService postApiService;

  public PostController(
      final PostApiService postApiService
  ) {
    this.postApiService = postApiService;
  }

  @PostMapping("/posts")
  public ResponseEntity<ApiResponse<PostResponseDto.itemDto>> createPost(
      @RequestHeader("userId") Long userId,
      @RequestBody final CreateRequest createRequest) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(postApiService.createPost(userId, createRequest)));
  }

  @GetMapping("/posts")
  public ResponseEntity<ApiResponse<PostResponseDto.ListDto>> getPosts(
      @RequestHeader(value = "userId") String userId
  ) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(postApiService.getAllPosts()));

  }

  @GetMapping("/posts/search")
  public ResponseEntity<ApiResponse<PostResponseDto.ListDto>> searchPostsByKeyword(
      @RequestHeader(value = "userId") String userId,
      @RequestParam(value = "keyword") final String keyword,
      @RequestParam(value = "searchSort", defaultValue = "POST_TITLE") final String searchSort
  ) {
    PostSearchSort postSearchSort = PostSearchSort.from(searchSort);

    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(
            postApiService.searchPostsByKeyword(postSearchSort, keyword)));
  }

  @GetMapping("/posts/{id}")
  public ResponseEntity<ApiResponse<PostResponseDto.itemDto>> getPostById(
      @RequestHeader(value = "userId") String userId,
      @PathVariable("id") final Long id) {

    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(postApiService.getPostById(id)));
  }

  @PutMapping("/posts")
  public ResponseEntity<ApiResponse<PostResponseDto.itemDto>> updatePostTitle(
      @RequestHeader(value = "userId") String userId,
      @RequestBody final UpdateRequest updateRequest) {

    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(
        postApiService.updatePostTitle(updateRequest)));
  }

  @DeleteMapping("/posts/{postId}")
  public ResponseEntity<ApiResponse<Object>> deletePostById(
      @RequestHeader(value = "userId") String userId,
      @PathVariable("postId") final Long postId) {

    postApiService.deletePostById(postId);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success());
  }
}
