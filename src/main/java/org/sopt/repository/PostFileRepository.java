package org.sopt.repository;

import java.util.List;
import org.sopt.domain.Post;
import org.sopt.util.DomainIdUtil;
import org.sopt.util.PostFileUtil;
import org.sopt.util.PostIdFileUtil;

public class PostFileRepository implements PostRepository {

  PostFileUtil fileUtil;
  DomainIdUtil postIdUtil;

  public PostFileRepository() {
    this.fileUtil = new PostFileUtil();
    this.postIdUtil = new DomainIdUtil(new PostIdFileUtil());
  }

  @Override
  public void save(final Post post) {
    if (post.getId() == null) {
      createNewPost(post);
      return;
    }

    replacePost(post);
  }

  @Override
  public List<Post> findAll() {
    return fileUtil.fetchAllPosts();
  }

  @Override
  public Post findOneById(final int id) {
    List<Post> postList = fileUtil.fetchAllPosts();
    for (Post post : postList) {
      if (post.getId() == id) {
        return post;
      }
    }

    return null;
  }

  @Override
  public boolean deleteById(final int deleteId) {
    List<Post> postList = fileUtil.fetchAllPosts();
    for (Post post : postList) {
      if (post.getId() == deleteId) {
        postList.remove(post);
        fileUtil.save(postList);
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean isExistByTitle(final String newTitle) {
    List<Post> postList = fileUtil.fetchAllPosts();
    return postList.stream().anyMatch(post -> post.getTitle().equals(newTitle));
  }

  @Override
  public List<Post> findPostsByTitleLike(final String keyword) {
    List<Post> postList = fileUtil.fetchAllPosts();
    return postList.stream().filter(post -> post.getTitle().contains(keyword)).toList();
  }

  private void replacePost(final Post post) {
    List<Post> postList = fileUtil.fetchAllPosts();
    postList.removeIf(savedPost -> savedPost.getId().equals(post.getId()));
    postList.add(post);
    fileUtil.save(postList);
  }

  private void createNewPost(final Post post) {
    List<Post> postList = fileUtil.fetchAllPosts();
    Post newPost = new Post(postIdUtil.generateId(), post.getTitle());
    postList.add(newPost);
    fileUtil.save(postList);
  }
}
