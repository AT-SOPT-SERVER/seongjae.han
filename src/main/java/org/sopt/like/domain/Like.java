package org.sopt.like.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.sopt.global.entity.BaseEntity;
import org.sopt.user.domain.User;

@Entity
@Table(
    name = "likes"
)
public class Like extends BaseEntity {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private Long targetId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private LikeTargetType likeTargetType;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;
}
