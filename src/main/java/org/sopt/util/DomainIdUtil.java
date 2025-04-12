package org.sopt.util;

public class DomainIdUtil {

  private Integer id = 0;
  PostIdFileUtil postIdFileUtil;

  public DomainIdUtil() {
    this.id = 0;
  }

  public DomainIdUtil(PostIdFileUtil postIdFileUtil) {
    this.id = postIdFileUtil.getGeneratedId();
    this.postIdFileUtil = postIdFileUtil;
  }

  public Integer generateId() {

    if (postIdFileUtil != null) {
      postIdFileUtil.saveGeneratedId(id+1);
    }

    return id++;
  }
}
