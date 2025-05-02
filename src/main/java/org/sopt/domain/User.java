package org.sopt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import org.sopt.exceptions.ApiException;
import org.sopt.exceptions.ErrorCode;
import org.sopt.util.GraphemeUtil;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "email", nullable = false)
  private String email;

  private static final int USER_NAME_MAX_LENGTH = 10;

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  protected User() {
  }

  protected User(final String name, final String email) {
    this.name = name;
    this.email = email;
  }

  public static User of(final String name, final String email) {
    throwIfFieldsBlank(name, email);
    throwIfNameLengthLong(name);

    return new User(name, email);
  }

  @OneToMany(mappedBy = "user")
  private List<Post> posts = new ArrayList<>();

  private static void throwIfFieldsBlank(final String name, final String email) {
    if (name == null || name.isBlank()) {
      throw new ApiException(ErrorCode.BLANK_USER_NAME);
    }

    if (email == null || email.isBlank()) {
      throw new ApiException(ErrorCode.BLANK_USER_EMAIL);
    }
  }

  private static void throwIfNameLengthLong(final String name) {
    int count = GraphemeUtil.count(name);
    if (count > USER_NAME_MAX_LENGTH) {
      throw new ApiException(ErrorCode.TOO_LONG_USER_NAME);
    }
  }
}
