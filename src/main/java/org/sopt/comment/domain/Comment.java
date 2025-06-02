package org.sopt.comment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;
import org.sopt.global.entity.BaseEntity;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "content", nullable = false)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  @Builder(access = AccessLevel.PRIVATE)
  public Comment(final String content, final User user, final Post post) {
    this.content = content;
    this.user = user;
    this.post = post;
  }

  public static Comment of(final String content, final User user, final Post post) {
    CommentValidator.validateCommentLength(content);

    return Comment
        .builder()
        .content(content)
        .user(user)
        .post(post)
        .build();
  }

  public void update(final String content) {
    CommentValidator.validateCommentLength(content);

    this.content = content;
  }
}
