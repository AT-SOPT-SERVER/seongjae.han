package org.sopt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.sopt.exceptions.ApiException;
import org.sopt.exceptions.ErrorCode;
import org.sopt.util.GraphemeUtil;

@Entity
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;

  public Post() {
  }

  public Post(String title) {
    throwIfTitleLengthLong(title);

    this.title = title;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
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