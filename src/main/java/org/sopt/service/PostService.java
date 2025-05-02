package org.sopt.service;

import java.util.List;
import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.dto.PostRequestDto.CreateRequest;
import org.sopt.dto.PostRequestDto.UpdateRequest;
import org.sopt.dto.PostResponseDto;
import org.sopt.dto.PostResponseDto.ListDto;
import org.sopt.exceptions.ApiException;
import org.sopt.exceptions.ErrorCode;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.sopt.util.TimeIntervalUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final TimeIntervalUtil postTimeIntervalUtil;

  public PostService(
      PostRepository postRepository,
      UserRepository userRepository,
      TimeIntervalUtil postTimeIntervalUtil
  ) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.postTimeIntervalUtil = postTimeIntervalUtil;
  }

  /**
   * 게시물 생성
   *
   * @param createRequest 게시물 생성 dto(제목, 내용)
   * @return 작성 게시글 dto
   */
  @Transactional
  public PostResponseDto.itemDto createPost(Long userId, CreateRequest createRequest) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_UNAUTHORIZED));

    // TODO: 개발 후 원상복귀
//    throwIfInputTimeIntervalNotValid();

    Post post = Post.of(createRequest.title(), createRequest.content(), user);

    if (postRepository.existsByTitle(post.getTitle())) {
      throw new ApiException(ErrorCode.DUPLICATE_POST_TITLE);
    }

    Post newPost = postRepository.save(post);
    postTimeIntervalUtil.startTimer();

    return new PostResponseDto.itemDto(newPost.getTitle(), newPost.getContent(),
        newPost.getUser().getName());
  }

  /**
   * 게시물 전체 리스트
   *
   * @return 게시물 리스트
   */
  @Transactional(readOnly = true)
  public PostResponseDto.ListDto getAllPosts() {

    return new ListDto(postRepository.findAllByOrderByCreatedAtDesc().stream()
        .map(post -> new ListDto.PostHeaderDto(post.getTitle(), post.getUser().getName()))
        .toList());
  }

  /**
   * 게시물 아이디로 검색
   *
   * @param id 게시물 아이디
   * @return 게시물
   */
  @Transactional(readOnly = true)
  public PostResponseDto.itemDto getPostById(final Long id) {
    Post post = postRepository.findFirstById(id)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_POST));

    return new PostResponseDto.itemDto(post.getTitle(), post.getContent(),
        post.getUser().getName());
  }

  /**
   * 게시물 삭제
   *
   * @param postId 삭제할 게시물 아이디
   */
  @Transactional
  public void deletePostById(final Long postId) {
    Post post = postRepository.findFirstById(postId)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_POST));

    postRepository.deleteById(post.getId());
  }


  @Transactional
  public PostResponseDto.itemDto updatePostTitle(final UpdateRequest updateRequest) {
    Post post = postRepository.findFirstById(updateRequest.id())
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_POST));

    if (postRepository.existsByTitle(updateRequest.title())) {
      throw new ApiException(ErrorCode.DUPLICATE_POST_TITLE);
    }

    post.update(updateRequest.title(), updateRequest.content());

    postRepository.save(post);

    return new PostResponseDto.itemDto(post.getTitle(), post.getContent(),
        post.getUser().getName());
  }

  /**
   * 게시물 제목으로 검색 (like %keyword%)
   *
   * @param keyword 검색 키워드
   * @return 게시물 리스트
   */
  @Transactional(readOnly = true)
  public PostResponseDto.ListDto findPostsByKeywordAndWriterName(
      final String searchSort,
      final String keyword
  ) {
    List<Post> posts = List.of();
    if (searchSort.equals("postTitle")) {
      posts = postRepository.findPostsByTitleContaining(keyword);
    } else if (searchSort.equals("writerName")) {
      posts = postRepository.findPostsByWriterNameContaining(keyword);
    }

    return new ListDto(posts.stream()
        .map(post -> new ListDto.PostHeaderDto(post.getTitle(), post.getUser().getName()))
        .toList());
  }

  /**
   * 게시글 작성 시간 validate 로직
   */
  private void throwIfInputTimeIntervalNotValid() {
    if (!postTimeIntervalUtil.isAvailable()) {
      throw new ApiException(ErrorCode.TOO_MANY_POST_REQUESTS);
    }
  }
}
