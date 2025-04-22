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

  public static class UpdateRequest {
    private Long id;
    private String title;

    public UpdateRequest() {
    }

    public UpdateRequest(final Long id, final String title) {
      this.id = id;
      this.title = title;
    }

    public String getTitle() {
      return title;
    }

    public Long getId() {
      return id;
    }
  }
}
