package org.sopt.service;

import java.util.List;
import org.sopt.domain.Post;
import org.sopt.exceptions.PostNotFoundException;
import org.sopt.repository.PostFileRepository;
import org.sopt.repository.PostRepository;

public class PostService {

  private final PostRepository postRepository = new PostFileRepository();

  /**
   * 게시물 생성
   * @param title 제목
   */
  public void createPost(String title) {
    Post post = new Post(title);
    if (postRepository.isExistByTitle(title)) {
      throw new RuntimeException("중복된 제목의 게시물입니다.");
    }
    postRepository.save(post);
  }

  /**
   * 게시물 전체 리스트
   * @return 게시물 리스트
   */
  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }

  /**
   * 게시물 아이디로 검색
   * @param id 게시물 아이디
   * @return 게시물
   */
  public Post getPostById(final int id) {
    return postRepository.findOneById(id);
  }

  /**
   * 게시물 삭제
   * @param deleteId 삭제할 게시물 아이디
   * @return 게시물 삭제 여부
   */
  public boolean deletePostById(final int deleteId) {
    return postRepository.deleteById(deleteId);
  }

  /**
   * 기존 게시물의 제목 update
   *
   * @param updateId 업데이트 할 게시물 아이디
   * @param newTitle 업데이트 할 게시물 제목
   */
  public void updatePostTitle(final int updateId, final String newTitle) {
    Post post = postRepository.findOneById(updateId);

    if (post == null) {
      throw new PostNotFoundException();
    }
    if (postRepository.isExistByTitle(newTitle)) {
      throw new RuntimeException("중복된 제목의 게시물입니다.");
    }

    post.setTitle(newTitle);
    postRepository.save(post);
  }

  /**
   * 게시물 제목으로 검색 (like %keyword%)
   * @param keyword 검색 키워드
   * @return 게시물 리스트
   */
  public List<Post> findPostsByKeyword(final String keyword) {
    return postRepository.findPostsByTitleLike(keyword);
  }
}
