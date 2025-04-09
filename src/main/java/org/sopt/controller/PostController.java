package org.sopt.controller;

import java.util.List;
import org.sopt.domain.Post;
import org.sopt.service.PostService;

public class PostController {

  private final PostService postService = new PostService();

  private int postId;

  public void createPost(String title) {
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

  public Boolean updatePostTitle(final int updateId, final String newTitle) {
      return postService.updatePostTitle(updateId, newTitle);
  }

  public List<Post> searchPostsByKeyword(final String keyword) {
    return null;
  }
}
