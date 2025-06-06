package org.sopt.comment.application.command;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.application.writer.CommentWriter;
import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentCreateServiceRequestDto;
import org.sopt.comment.application.dto.CommentServiceResponseDto.CommentItemDto;
import org.sopt.comment.domain.Comment;
import org.sopt.post.application.reader.PostReader;
import org.sopt.post.domain.Post;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCommentServiceImpl implements CreateCommentService {

  private final PostReader postReader;
  private final UserReader userReader;
  private final CommentWriter commentWriter;

  @Override
  public CommentItemDto execute(final CommentCreateServiceRequestDto commentCreateRequestDto) {
    final User user = userReader.getUserOrThrow(commentCreateRequestDto.userId());
    final Post post = postReader.getPostOrThrow(commentCreateRequestDto.postId());

    final Comment comment = Comment.of(commentCreateRequestDto.content(), user, post);
    final Comment saved = commentWriter.save(comment);

    return CommentItemDto.from(saved);
  }

}
