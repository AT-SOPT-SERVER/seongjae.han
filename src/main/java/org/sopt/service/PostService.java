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
}
