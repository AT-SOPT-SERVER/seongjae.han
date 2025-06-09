package org.sopt.post.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.comment.application.dto.CommentServiceResponseDto.CommentListDto;
import org.sopt.post.domain.Post;
import org.sopt.post.application.dto.PostServiceResponseDto.PostItemServiceResponse;
import org.sopt.post.application.dto.PostServiceResponseDto.PostListServiceResponse;

public sealed interface PostServiceResponseDto permits PostListServiceResponse,
    PostItemServiceResponse {

  @Builder(access = AccessLevel.PROTECTED)
  record PostListServiceResponse(List<PostHeaderDto> postHeaders) implements
      PostServiceResponseDto {

    public static PostListServiceResponse from(final List<Post> posts) {

      return PostListServiceResponse.builder().postHeaders(
          posts.stream()
              .map(PostHeaderDto::from)
              .toList()
      ).build();
    }

    @Builder(access = AccessLevel.PROTECTED)
    public record PostHeaderDto(Long postId, String title, String writerName) {

      public static PostHeaderDto from(Post post) {

        // TODO: n+1 방어
        return PostHeaderDto.builder()
            .postId(post.getId())
            .title(post.getTitle())
            .writerName(post.getUser().getName())
            .build();
      }
    }
  }

  @Builder(access = AccessLevel.PROTECTED)
  record PostItemServiceResponse(String title, String content, String writerName,
                                 CommentListDto commentListDto) implements PostServiceResponseDto {

    public static PostItemServiceResponse from(final Post post) {
      return PostItemServiceResponse.builder()
          .title(post.getTitle())
          .commentListDto(CommentListDto.from(post.getComments()))
          .content(post.getContent())
          .writerName(post.getUser().getName())
          .build();
    }
  }
}
