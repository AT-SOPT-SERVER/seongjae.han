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

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "email", nullable = false)
  private String email;

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
}
