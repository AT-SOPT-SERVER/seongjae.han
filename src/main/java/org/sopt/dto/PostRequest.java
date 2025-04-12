package org.sopt.dto;

public class PostRequest {

  private String title;

  public PostRequest() {
  }

  public PostRequest(final String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }
}
