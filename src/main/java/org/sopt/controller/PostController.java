package org.sopt.controller;

import java.util.List;
import org.sopt.domain.Post;
import org.sopt.service.PostService;

public class PostController {

  private final PostService postService = new PostService();

  private int postId;

  public void createPost(String title) {
    throwIfTitleNotValid(title);
    Post post = new Post(postId++, title);
    postService.createPost(post);
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
    throwIfTitleNotValid(newTitle);

    return postService.updatePostTitle(updateId, newTitle);
  }

  public List<Post> searchPostsByKeyword(final String keyword) {
    return null;
  }

  /**
   *
   * @param inputTitle 입력된 제목
   */
  private void throwIfTitleNotValid(final String inputTitle) {
    if (inputTitle.isBlank()) {
      throw new IllegalArgumentException("제목이 비어있습니다.");
    }

    if (inputTitle.length() > 30) {
      throw new IllegalArgumentException("제목이 30자를 넘지 않도록 해주세요.");
    }
  }
}
