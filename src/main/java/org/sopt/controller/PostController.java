package org.sopt.controller;

import java.util.List;
import java.text.BreakIterator;
import java.util.Optional;
import org.sopt.domain.Post;
import org.sopt.dto.PostRequestDto.CreateRequest;
import org.sopt.dto.PostRequestDto.UpdateRequest;
import org.sopt.exceptions.ApiException;
import org.sopt.exceptions.ErrorCode;
import org.sopt.responses.ApiResponse;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

  private final PostService postService;

  public PostController(
      final PostService postService
  ) {
    this.postService = postService;
  }

  @PostMapping("/posts")
  public ResponseEntity<ApiResponse<Post>> createPost(
      @RequestBody final CreateRequest createRequest) {
    throwIfTitleInputNotValid(createRequest.title());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(postService.createPost(createRequest.title())));
  }

  @GetMapping("/posts")
  public ResponseEntity<ApiResponse<List<Post>>> getPosts(
      @RequestParam(value = "keyword") final Optional<String> keyword) {
    if (keyword.isEmpty()) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(ApiResponse.success(postService.getAllPosts()));
    }

    String trimmed = keyword.get().trim();
    if (trimmed.isEmpty()) {
      return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(List.of()));
    }

    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(postService.findPostsByKeyword(trimmed)));
  }

  @GetMapping("/posts/{id}")
  public ResponseEntity<ApiResponse<Post>> getPostById(@PathVariable("id") final Long id) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(postService.getPostById(id)));
  }

  @PutMapping("/posts")
  public ResponseEntity<ApiResponse<Post>> updatePostTitle(
      @RequestBody final UpdateRequest updateRequest) {

    throwIfTitleInputNotValid(updateRequest.title());

    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(
        postService.updatePostTitle(updateRequest.id(), updateRequest.title())));
  }

  @DeleteMapping("/posts/{postId}")
  public ResponseEntity<ApiResponse<Object>> deletePostById(
      @PathVariable("postId") final Long postId) {
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success());
  }

  /**
   * 제목이 입력 규칙에 맞게 입력되지 않은 경우 예외 throw
   *
   * @param inputTitle 입력된 제목
   */
  private void throwIfTitleInputNotValid(final String inputTitle) {
    if (inputTitle.isBlank()) {
      throw new ApiException(ErrorCode.BLANK_POST_TITLE);
    }
  }
}
