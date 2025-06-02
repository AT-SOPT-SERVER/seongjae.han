package org.sopt.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import org.sopt.post.PostTag;
import org.sopt.user.domain.User;
import org.sopt.global.entity.BaseEntity;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.global.util.GraphemeUtil;

@Entity
@Table(name = "post", indexes = @Index(name = "idx_post_tag", columnList = "tag"))
@Getter
public class Post extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "content", nullable = false)
  private String content;

  @Enumerated(EnumType.STRING)
  private PostTag tag;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  private static final int POST_CONTENT_MAX_LENGTH = 1000;
  private static final int POST_TITLE_MAX_LENGTH = 30;

  protected Post() {
  }

  protected Post(String title, String content, User user) {
    this.title = title;
    this.content = content;
    this.user = user;
  }

  public static Post of(String title, String content, User user) {
    throwIfFieldsBlank(title, content);
    throwIfTitleLengthLong(title);
    throwIfContentLengthLong(content);

    return new Post(title, content, user);
  }

  public void update(final String newTitle, final String content) {
    throwIfFieldsBlank(newTitle, content);
    throwIfTitleLengthLong(title);
    throwIfContentLengthLong(content);

    this.title = newTitle;
    this.content = content;
  }

  private static void throwIfFieldsBlank(final String title, final String content) {
    if (title == null || title.isBlank()) {
      throw new ApiException(ErrorCode.BLANK_POST_TITLE);
    }

    if (content == null || content.isBlank()) {
      throw new ApiException(ErrorCode.BLANK_POST_CONTENT);
    }
  }

  private static void throwIfTitleLengthLong(final String title) {
    int count = GraphemeUtil.count(title);
    if (count > POST_TITLE_MAX_LENGTH) {
      throw new ApiException(ErrorCode.TOO_LONG_POST_TITLE);
    }
  }

  private static void throwIfContentLengthLong(final String content) {
    int count = GraphemeUtil.count(content);
    if (count > POST_CONTENT_MAX_LENGTH) {
      throw new ApiException(ErrorCode.TOO_LONG_POST_CONTENT);
    }
  }

}