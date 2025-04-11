package org.sopt.controller;

import java.util.List;
import org.sopt.domain.Post;
import org.sopt.exceptions.PostNotFoundException;
import org.sopt.service.PostService;
import org.sopt.util.TimeIntervalUtil;

public class PostController {

  private final PostService postService = new PostService();

  private final TimeIntervalUtil postTimeIntervalUtil = new TimeIntervalUtil();

  private int postId;

  public void createPost(String title) {
    throwIfInputTimeIntervalNotValid();
    throwIfTitleInputNotValid(title);

    postService.createPost(title);
    postTimeIntervalUtil.startTimer();
  }

  public List<Post> getAllPosts() {
    return postService.getAllPosts();
  }

  public Post getPostById(final int id) {
    return postService.getPostById(id);
  }

  public boolean deletePostById(final int deleteId) {
    return postService.deletePostById(deleteId);
  }

  public Boolean updatePostTitle(final Integer updateId, final String newTitle) {

    throwIfTitleInputNotValid(newTitle);

    try {
      postService.updatePostTitle(updateId, newTitle);
      return true;
    } catch (PostNotFoundException e) {
      return false;
    }
  }

  public List<Post> searchPostsByKeyword(final String keyword) {
    throwIfKeywordInputNotValid(keyword);

    return postService.findPostsByKeyword(keyword);
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

    if (inputTitle.length() > 30) {
      throw new IllegalArgumentException("제목이 30자를 넘지 않도록 해주세요.");
    }
  }

  private void throwIfInputTimeIntervalNotValid() {
    if (!postTimeIntervalUtil.isAvailable()) {
      throw new IllegalArgumentException("아직 새로운 게시물을 작성하실 수 없습니다.");
    }
  }
}
