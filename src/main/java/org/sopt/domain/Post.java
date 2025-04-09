package org.sopt.domain;

public class Post {

  private Integer id = null;
  private String title;

  public Post(String title) {
    this.title = title;
  }

  public Post(Integer id, String title) {
    this.id = id;
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
}
