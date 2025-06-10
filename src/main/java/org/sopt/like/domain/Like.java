package org.sopt.like.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.global.entity.BaseEntity;
import org.sopt.user.domain.User;

@Entity
@Table(
    name = "likes",
    indexes = {
        @Index(name = "idx_target_type_id", columnList = "targetType, targetId")
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TargetType targetType;

  @Column(nullable = false)
  private Long targetId;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  protected Like(final TargetType targetType, final Long targetId, final User user) {
    this.targetType = targetType;
    this.targetId = targetId;
    this.user = user;
  }

  public static Like ofPost(Long postId, User user) {
    return Like.of(TargetType.POST, postId, user);
  }

  public static Like ofComment(Long commentId, User user) {
    return Like.of(TargetType.COMMENT, commentId, user);
  }

  protected static Like of(TargetType targetType, Long targetId, User user) {
    return new Like(targetType, targetId, user);
  }
}
