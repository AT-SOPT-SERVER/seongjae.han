package org.sopt.domain;

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

  private String title;

  private String content;

  protected Post() {
  }

  public Post(String title, String content) {
    this.title = title;
    this.content = content;
  }

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public void updateTitle(final String newTitle) {
    throwIfTitleLengthLong(title);

    this.title = newTitle;
  }

  private static void throwIfTitleLengthLong(final String title) {
    int count = GraphemeUtil.count(title);
    if (count > 30) {
      throw new ApiException(ErrorCode.TOO_LONG_POST_TITLE);
    }
  }

}