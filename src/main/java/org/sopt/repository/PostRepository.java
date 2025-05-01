package org.sopt.repository;

import java.util.List;
import java.util.Optional;
import org.sopt.domain.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  boolean existsByTitle(String title);

  Optional<Post> findFirstById(Long id);

  @Override
  @EntityGraph(attributePaths = {"user"})
  List<Post> findAll();

  List<Post> findPostsByTitleContaining(String keyword);
}