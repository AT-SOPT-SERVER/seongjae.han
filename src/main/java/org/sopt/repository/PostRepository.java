package org.sopt.repository;

import java.util.List;
import org.sopt.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  boolean existsByTitle(String title);

  Post findFirstById(Long id);

  List<Post> findPostsByTitleContaining(String keyword);
}