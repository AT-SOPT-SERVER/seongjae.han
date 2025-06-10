package org.sopt.like.application.writer;

import java.util.List;
import org.sopt.like.domain.Like;

public interface LikeWriter {

  Like save(Like like);

  void deleteBatch(List<Like> likes);
}
