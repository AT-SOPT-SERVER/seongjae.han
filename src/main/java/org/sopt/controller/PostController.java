package org.sopt.controller;

import java.text.BreakIterator;
import java.util.List;
import org.sopt.domain.Post;
import org.sopt.dto.PostRequestDto;
import org.sopt.service.PostService;
import org.sopt.exceptions.PostNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

  private final PostService postService;

  public PostController(
      final PostService postService
  ) {
    this.postService = postService;
  }

  @PostMapping("/post")
  public void createPost(@RequestBody final PostRequestDto.PostCreateRequestDto postRequest) {
    throwIfTitleInputNotValid(postRequest.title());

    postService.createPost(postRequest.title());
  }

  @GetMapping("/posts")
  public List<Post> getAllPosts() {
    return postService.getAllPosts();
  }

  @GetMapping("/posts/search")
  public List<Post> searchPostsByKeyword(@RequestParam(value = "keyword") final String keyword) {
    throwIfKeywordInputNotValid(keyword);

    return postService.findPostsByKeyword(keyword);
  }

  @GetMapping("/post/{id}")
  public Post getPostById(@PathVariable("id") final Long id) {
    return postService.getPostById(id);
  }

  @PutMapping("/post")
  public Boolean updatePostTitle(@RequestBody final  PostRequestDto.PostUpdateRequestDto updateRequest) {

    throwIfTitleInputNotValid(updateRequest.title());

    try {
      postService.updatePostTitle(updateRequest.id(), updateRequest.title());
      return true;
    } catch (PostNotFoundException e) {
      return false;
    }
  }

  @DeleteMapping("/post/{deleteId}")
  public void deletePostById(@PathVariable("deleteId") final Long deleteId) {
    postService.deletePostById(deleteId);
  }

  private void throwIfKeywordInputNotValid(final String keyword) {
    if (keyword.isBlank()) {
      throw new IllegalArgumentException("입력이 비어있습니다.");
    }
  }

  /**
   * 제목이 입력 규칙에 맞게 입력되지 않은 경우 예외 throw
   *
   * @param inputTitle 입력된 제목
   */
  private void throwIfTitleInputNotValid(final String inputTitle) {
    if (inputTitle.isBlank()) {
      throw new IllegalArgumentException("제목이 비어있습니다.");
    }
    int count = this.getGraphemeCount(inputTitle);

    if (count > 30) {
      throw new IllegalArgumentException("제목이 30자를 넘지 않도록 해주세요.");
    }
  }

  private int getGraphemeCount(String text) {
    BreakIterator it = BreakIterator.getCharacterInstance();
    it.setText(text);
    int count = 0;
    while (it.next() != BreakIterator.DONE) {
      count++;
    }
    return count;
  }
}
