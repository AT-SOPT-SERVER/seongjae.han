package org.sopt.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.sopt.post.domain.Post;
import org.sopt.global.entity.BaseEntity;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.global.util.GraphemeUtil;

@Getter
@Entity
@NoArgsConstructor
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "email", nullable = false)
  private String email;

  private static final int USER_NAME_MAX_LENGTH = 10;

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
