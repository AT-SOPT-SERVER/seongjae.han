package org.sopt.repository;

import java.util.ArrayList;
import java.util.List;
import org.sopt.domain.Post;

public class PostRepository {

  List<Post> postList = new ArrayList<>();

  public void save(Post post) {
    postList.removeIf(savedPost -> savedPost.getId() == post.getId());
    postList.add(post);
  }

  public List<Post> findAll() {
    return this.postList;
  }

  public Post findOneById(final int id) {
    for (Post post : postList) {
      if (post.getId() == id) {
        return post;
      }
    }

    return null;
  }

  public boolean deleteById(final int deleteId) {
    for (Post post : postList) {
      if (post.getId() == deleteId) {
        postList.remove(post);
        return true;
      }
    }

    return false;
  }
}
