package org.sopt.repository;

import java.util.List;
import org.sopt.domain.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository {

  public void save(Post post);

  public List<Post> findAll();

  public Post findOneById(final int id);

  public boolean deleteById(final int deleteId);

  public boolean isExistByTitle(final String newTitle);

  public List<Post> findPostsByTitleLike(final String keyword);

}
