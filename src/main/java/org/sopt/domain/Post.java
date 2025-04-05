package org.sopt.domain;

public class Post {

  private final int id;
  private final String title;

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
}
