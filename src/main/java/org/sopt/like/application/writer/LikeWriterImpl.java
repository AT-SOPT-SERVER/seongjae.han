package org.sopt.like.application.writer;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.like.domain.Like;
import org.sopt.like.domain.LikeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeWriterImpl implements LikeWriter {

  private final LikeRepository likeRepository;

  @Override
  public Like save(final Like like) {

    return likeRepository.save(like);
  }

  @Override
  public void deleteBatch(final List<Like> likes) {
    likeRepository.deleteAllInBatch(likes);
  }
}
