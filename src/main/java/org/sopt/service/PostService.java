package org.sopt.service;

import java.util.List;
import org.sopt.domain.Post;
import org.sopt.exceptions.PostNotFoundException;
import org.sopt.repository.PostRepository;
import org.sopt.util.TimeIntervalUtil;
import org.springframework.stereotype.Service;

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
   *
   * @param title 제목
   */
  public Post createPost(String title) {
    throwIfInputTimeIntervalNotValid();
    Post post = new Post(title);
    if (postRepository.existsByTitle(title)) {
      throw new RuntimeException("중복된 제목의 게시물입니다.");
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
  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }

  /**
   * 게시물 아이디로 검색
   *
   * @param id 게시물 아이디
   * @return 게시물
   */
  public Post getPostById(final Long id) {
    return postRepository.findFirstById(id)
        .orElseThrow(() -> new RuntimeException("게시물이 존재하지 않습니다."));
  }

  /**
   * 게시물 삭제
   *
   * @param postId 삭제할 게시물 아이디
   */
  public void deletePostById(final Long postId) {
    postRepository.deleteById(postId);
  }

  /**
   * 기존 게시물의 제목 update
   *
   * @param updateId 업데이트 할 게시물 아이디
   * @param newTitle 업데이트 할 게시물 제목
   */
  public void updatePostTitle(final Long updateId, final String newTitle) {
    Post post = postRepository.findFirstById(updateId)
        .orElseThrow(() -> new RuntimeException("게시물이 존재하지 않습니다."));

    if (postRepository.existsByTitle(newTitle)) {
      throw new RuntimeException("중복된 제목의 게시물입니다.");
    }

    post.setTitle(newTitle);
    postRepository.save(post);
  }

  /**
   * 게시물 제목으로 검색 (like %keyword%)
   *
   * @param keyword 검색 키워드
   * @return 게시물 리스트
   */
  public List<Post> findPostsByKeyword(final String keyword) {
    return postRepository.findPostsByTitleContaining(keyword);
  }

  private void throwIfInputTimeIntervalNotValid() {
    if (!postTimeIntervalUtil.isAvailable()) {
      throw new IllegalArgumentException("아직 새로운 게시물을 작성하실 수 없습니다.");
    }
  }
}
