package org.sopt.post.infrastructure.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.post.PostTag;
import org.sopt.post.application.query.PostSearchSort;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostQueryRepository;
import org.sopt.post.domain.QPost;
import org.sopt.user.domain.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  private final QPost post = QPost.post;
  private final QUser user = QUser.user;

  @Override
  public List<Post> getPosts() {
    QPost post = QPost.post;

    return jpaQueryFactory
        .selectFrom(post)
        .orderBy(post.id.desc())
        .fetch();
  }

  @Override
  public Page<Post> getPosts(final Pageable pageRequest) {
    QPost post = QPost.post;

    List<Post> content = jpaQueryFactory
        .selectFrom(post)
        .offset(pageRequest.getOffset())
        .limit(pageRequest.getPageSize())
        .orderBy(post.id.desc())
        .fetch();

    long total = jpaQueryFactory
        .select(post.count())
        .from(post)
        .fetchOne();

    return new PageImpl<>(content, pageRequest, total);
  }

  @Override
  public Page<Post> searchPosts(Pageable pageable, PostSearchSort sort, String keyword) {
    BooleanExpression predicate = buildPredicate(sort, keyword);

    List<Post> content = fetchContent(predicate, pageable);
    long total = fetchCount(predicate);

    return new PageImpl<>(content, pageable, total);
  }

  private List<Post> fetchContent(BooleanExpression predicate, Pageable pageable) {
    return jpaQueryFactory
        .selectFrom(post)
        .leftJoin(post.user, user).fetchJoin()
        .where(predicate)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(post.id.desc())
        .fetch();
  }

  private long fetchCount(BooleanExpression predicate) {
    Long count = jpaQueryFactory
        .select(post.count())
        .from(post)
        .leftJoin(post.user, user)
        .where(predicate)
        .fetchOne();

    return count != null ? count : 0L;
  }


  private BooleanExpression buildPredicate(final PostSearchSort sort, final String keyword) {
    return switch (sort) {
      case POST_TITLE -> containsIgnoreCase(post.title, keyword);
      case WRITER_NAME -> containsIgnoreCase(user.name, keyword);
      case POST_TAG -> post.tag.eq(PostTag.from(keyword));
    };
  }

  private BooleanExpression containsIgnoreCase(final StringPath field, final String keyword) {
    return (keyword == null || keyword.isBlank()) ? null : field.containsIgnoreCase(keyword);
  }
}
