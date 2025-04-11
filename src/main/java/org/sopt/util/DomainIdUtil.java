package org.sopt.util;

public class DomainIdUtil {

  private Integer id = 0;
  FileRepositoryUtil fileRepositoryUtil;

  public DomainIdUtil() {
    this.id = 0;
  }

  public DomainIdUtil(FileRepositoryUtil fileRepositoryUtil) {
    this.id = fileRepositoryUtil.getGeneratedId();
    this.fileRepositoryUtil = fileRepositoryUtil;
  }

  public Integer generateId() {

    if (fileRepositoryUtil != null) {
      fileRepositoryUtil.saveGeneratedId(id+1);
    }

    return id++;
  }
}
