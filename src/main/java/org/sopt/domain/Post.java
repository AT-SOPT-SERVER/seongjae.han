package org.sopt.domain;

public class Post {

  private final int id;
  private String title;

  public Post(int id, String title) {
    this.id = id;
    this.title = title;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(final String newTitle) {
    this.title = newTitle;
  }
}
