package org.sopt.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.global.entity.BaseEntity;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.global.util.GraphemeUtil;
import org.sopt.post.domain.Post;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "loginId", nullable = false)
  private String loginId;

  @Column(name = "password", nullable = false)
  private String password;

  protected User(final String name, final String email, final String loginId, final String password) {
    this.name = name;
    this.email = email;
    this.loginId = loginId;
    this.password = password;
  }

  private static final int USER_NAME_MAX_LENGTH = 10;

  public static User createUser(final String loginId, final String password, final String name,
      final String email) {
    throwIfFieldsBlank(name, email);
    throwIfNameLengthLong(name);

    return new User(name, email, loginId, password);
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

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (this.getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
