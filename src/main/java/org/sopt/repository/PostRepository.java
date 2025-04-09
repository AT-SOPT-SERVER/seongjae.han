package org.sopt.repository;

import java.util.ArrayList;
import java.util.List;
import org.sopt.domain.Post;
import org.sopt.util.DomainIdUtil;

public class PostRepository {

  List<Post> postList = new ArrayList<>();
  DomainIdUtil postIdUtil = new DomainIdUtil();

  public void save(Post post) {
    if (post.getId() == null) {
      createNewPost(post);
      return;
    }

    replacePost(post);
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

  public boolean isExistByTitle(final String newTitle) {
    return postList.stream().anyMatch(post -> post.getTitle().equals(newTitle));
  }

  private void replacePost(final Post post) {
    postList.removeIf(savedPost -> savedPost.getId().equals(post.getId()));
    postList.add(post);
  }

  private void createNewPost(final Post post) {
    Post newPost = new Post(postIdUtil.generateId(), post.getTitle());
    postList.add(newPost);
  }
}
