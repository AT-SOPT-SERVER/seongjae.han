package org.sopt.service;

import java.util.List;
import org.sopt.domain.Post;
import org.sopt.dto.PostRequestDto.CreateRequest;
import org.sopt.dto.PostRequestDto.UpdateRequest;
import org.sopt.exceptions.ApiException;
import org.sopt.exceptions.ErrorCode;
import org.sopt.repository.PostRepository;
import org.sopt.util.TimeIntervalUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class PostService {

  private final PostRepository postRepository;
  private final TimeIntervalUtil postTimeIntervalUtil;

  public PostService(
      TimeIntervalUtil postTimeIntervalUtil,
      PostRepository postRepository
  ) {
    this.postTimeIntervalUtil = postTimeIntervalUtil;
    this.postRepository = postRepository;
  }

  /**
   * 게시물 생성
   * @param createRequest 게시물 생성 dto(제목, 내용)
   * @return 작성 게시글 dto
   */
  @Transactional
  public Post createPost(CreateRequest createRequest) {
    throwIfInputTimeIntervalNotValid();

    Post post = Post.of(createRequest.title(), createRequest.content());

    if (postRepository.existsByTitle(post.getTitle())) {
      throw new ApiException(ErrorCode.DUPLICATE_POST_TITLE);
    }

    Post newPost = postRepository.save(post);
    postTimeIntervalUtil.startTimer();

    return newPost;
  }

  /**
   * 게시물 전체 리스트
   *
   * @return 게시물 리스트
   */
  @Transactional(readOnly = true)
  public List<Post> getAllPosts() {

    return postRepository.findAll();
  }

  /**
   * 게시물 아이디로 검색
   *
   * @param id 게시물 아이디
   * @return 게시물
   */
  @Transactional(readOnly = true)
  public Post getPostById(final Long id) {
    return postRepository.findFirstById(id)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_POST));
  }

  /**
   * 게시물 삭제
   *
   * @param postId 삭제할 게시물 아이디
   */
  @Transactional
  public void deletePostById(final Long postId) {
    postRepository.deleteById(postId);
  }


  @Transactional
  public Post updatePostTitle(final UpdateRequest updateRequest) {
    Post post = postRepository.findFirstById(updateRequest.id())
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_POST));

    if (postRepository.existsByTitle(updateRequest.title())) {
      throw new ApiException(ErrorCode.DUPLICATE_POST_TITLE);
    }

    post.update(updateRequest.title(), updateRequest.content());

    return postRepository.save(post);
  }

  /**
   * 게시물 제목으로 검색 (like %keyword%)
   *
   * @param keyword 검색 키워드
   * @return 게시물 리스트
   */
  @Transactional(readOnly = true)
  public List<Post> findPostsByKeyword(final String keyword) {
    return postRepository.findPostsByTitleContaining(keyword);
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
