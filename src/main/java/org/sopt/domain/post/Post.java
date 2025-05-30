package org.sopt.domain.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.sopt.domain.user.User;
import org.sopt.global.common.entity.BaseEntity;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.global.util.GraphemeUtil;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "post", indexes = @Index(name = "idx_post_tag", columnList = "tag"))
public class Post extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "content", nullable = false)
  private String content;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @Enumerated(EnumType.STRING)
  private PostTag tag;

  @LastModifiedDate
  private LocalDateTime updatedAt;

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

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public User getUser() {
    return user;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
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