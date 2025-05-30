package org.sopt.domain.post;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  boolean existsByTitle(String title);

  Optional<Post> findFirstById(Long id);

  @EntityGraph(attributePaths = {"user"})
  List<Post> findAllByOrderByCreatedAtDesc();

  List<Post> findPostsByTitleContaining(String keyword);

  @Query(
      value = "select post FROM Post post JOIN FETCH post.user user"
          + " where  user.name like concat('%', :keyword , '%') "
  )
  List<Post> findPostsByWriterNameContaining(@Param("keyword") String keyword);

  List<Post> findPostsByTag(PostTag postTag);
}