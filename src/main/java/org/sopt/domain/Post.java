package org.sopt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.mapping.Join;
import org.sopt.exceptions.ApiException;
import org.sopt.exceptions.ErrorCode;
import org.sopt.util.GraphemeUtil;

@Entity
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "content", nullable = false)
  private String content;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

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

  public void update(final String newTitle, final String content) {
    throwIfFieldsBlank(newTitle, content);
    throwIfTitleLengthLong(title);

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
    if (count > 30) {
      throw new ApiException(ErrorCode.TOO_LONG_POST_TITLE);
    }
  }

}