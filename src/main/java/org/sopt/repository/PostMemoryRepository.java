package org.sopt.repository;

import java.util.ArrayList;
import java.util.List;
import org.sopt.domain.Post;
import org.sopt.util.DomainIdUtil;

public class PostMemoryRepository implements PostRepository {

  List<Post> postList = new ArrayList<>();
  DomainIdUtil postIdUtil = new DomainIdUtil();

  @Override
  public void save(Post post) {
    if (post.getId() == null) {
      createNewPost(post);
      return;
    }

    replacePost(post);
  }

  @Override
  public List<Post> findAll() {
    return this.postList;
  }

  @Override
  public Post findOneById(final int id) {
    for (Post post : postList) {
      if (post.getId() == id) {
        return post;
      }
    }

    return null;
  }

  @Override
  public boolean deleteById(final int deleteId) {
    for (Post post : postList) {
      if (post.getId() == deleteId) {
        postList.remove(post);
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean isExistByTitle(final String newTitle) {
    return postList.stream().anyMatch(post -> post.getTitle().equals(newTitle));
  }

  @Override
  public List<Post> findPostsByTitleLike(final String keyword) {
    return postList.stream().filter(post -> post.getTitle().contains(keyword)).toList();
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
