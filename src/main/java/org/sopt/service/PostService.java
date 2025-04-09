package org.sopt.service;

import java.util.List;
import org.sopt.domain.Post;
import org.sopt.exceptions.PostNotFoundException;
import org.sopt.repository.PostRepository;

public class PostService {

  private final PostRepository postRepository = new PostRepository();

  public void createPost(String title) {
    Post post = new Post(title);
    if (postRepository.isExistByTitle(title)) {
      throw new RuntimeException("중복된 제목의 게시물입니다.");
    }
    postRepository.save(post);
  }


  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }

  public Post getPostById(final int id) {
    return postRepository.findOneById(id);
  }

  public boolean deletePostById(final int deleteId) {
    return postRepository.deleteById(deleteId);
  }

  /**
   * 기존 게시물의 제목 update
   *
   * @param updateId 업데이트 할 게시물 아이디
   * @param newTitle 업데이트 할 게시물 제목
   */
  public void updatePostTitle(final int updateId, final String newTitle) {
    Post post = postRepository.findOneById(updateId);

    if (post == null) {
      throw new PostNotFoundException();
    }
    if (postRepository.isExistByTitle(newTitle)) {
      throw new RuntimeException("중복된 제목의 게시물입니다.");
    }

    post.setTitle(newTitle);
    postRepository.save(post);
  }
}
