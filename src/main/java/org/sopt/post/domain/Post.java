package org.sopt.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.sopt.comment.domain.Comment;
import org.sopt.post.PostTag;
import org.sopt.user.domain.User;
import org.sopt.global.entity.BaseEntity;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.global.util.GraphemeUtil;

@Entity
@Table(name = "post", indexes = @Index(name = "idx_post_tag", columnList = "tag"))
@Getter
public class Post extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "content", nullable = false)
  private String content;

  @Enumerated(EnumType.STRING)
  private PostTag tag;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();

  protected Post() {
  }

  protected Post(String title, String content, User user) {
    this.title = title;
    this.content = content;
    this.user = user;
  }

  public static Post of(String title, String content, User user) {
    PostValidator.throwIfFieldsBlank(title, content);
    PostValidator.throwIfTitleLengthLong(title);
    PostValidator.throwIfContentLengthLong(content);

    return new Post(title, content, user);
  }

  public void update(final String newTitle, final String content) {
    PostValidator.throwIfFieldsBlank(newTitle, content);
    PostValidator.throwIfTitleLengthLong(title);
    PostValidator.throwIfContentLengthLong(content);

    this.title = newTitle;
    this.content = content;
  }

  public void clearAllComments() {
    this.comments.clear();
  }
}