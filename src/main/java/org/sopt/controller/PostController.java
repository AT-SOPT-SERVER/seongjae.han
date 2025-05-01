package org.sopt.controller;

import java.util.List;
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

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(postService.createPost(createRequest)));
  }

  @GetMapping("/posts")
  public ResponseEntity<ApiResponse<List<Post>>> getPosts() {

      return ResponseEntity.status(HttpStatus.OK)
          .body(ApiResponse.success(postService.getAllPosts()));

  }

  @GetMapping("/posts/search")
  public ResponseEntity<ApiResponse<List<Post>>> searchPostsByKeyword(
      @RequestParam(value = "keyword") final String keyword) {

    if (keyword.isBlank()) {
      return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(List.of()));
    }

    String trimmed = keyword.trim();

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


    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(
        postService.updatePostTitle(updateRequest)));
  }

  @DeleteMapping("/posts/{postId}")
  public ResponseEntity<ApiResponse<Object>> deletePostById(
      @PathVariable("postId") final Long postId) {

    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success());
  }
}
