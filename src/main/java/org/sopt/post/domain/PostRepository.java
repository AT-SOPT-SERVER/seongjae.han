package org.sopt.post.domain;

import java.util.List;
import java.util.Optional;
import org.sopt.post.PostTag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  // 상세 조회 전용
  @EntityGraph(attributePaths = {"comments", "comments.user"})
  @Query("select p from Post p where p.id = :postId")
  Optional<Post> findWithCommentsUsersById(@Param("postId") Long postId);

  boolean existsByTitle(String title);

  @EntityGraph(attributePaths = {"user"})
  List<Post> findAllByOrderByCreatedAtDesc();

  @EntityGraph(attributePaths = {"user"})
  List<Post> findPostsByTitleContaining(String keyword);

  @Query(
      value = "select post FROM Post post JOIN FETCH post.user user"
          + " where  user.name like concat('%', :keyword , '%') "
  )
  List<Post> findPostsByWriterNameContaining(@Param("keyword") String keyword);

  @EntityGraph(attributePaths = {"user"})
  List<Post> findPostsByTag(PostTag postTag);
}