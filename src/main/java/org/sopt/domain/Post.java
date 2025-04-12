package org.sopt.domain;

import java.io.Serializable;

public class Post implements Serializable {

  private Integer id = null;
  private String title;

  public Post(Integer id, String title) {
    this.id = id;
    this.title = title;
  }

  public Post(String title) {
    this.title = title;
  }

  public Integer getId() {
    return id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(final String newTitle) {
    this.title = newTitle;
  }

  @Override
  public String toString() {
    return "Post{" +
        "id=" + id +
        ", title='" + title + '\'' +
        '}';
  }
}
