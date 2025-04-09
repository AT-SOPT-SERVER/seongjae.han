package org.sopt.service;

import java.util.List;
import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;

public class PostService {

  private final PostRepository postRepository = new PostRepository();

  public void createPost(Post post) {
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

  public Boolean updatePostTitle(final int updateId, final String newTitle) {
    Post post = postRepository.findOneById(updateId);

    if (post == null) {
      throw new RuntimeException("존재하지 않는 post 입니다.");
    }

    post.setTitle(newTitle);
    postRepository.save(post);

    return true;
  }
}
