package org.sopt.global.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sopt.global.constants.AppConstants.DEFAULT_PAGE_SIZE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("페이지네이션 유틸 테스트")
class PaginationUtilsTest {

  @DisplayName("0보다 큰 값의 페이지는 보정하지 않는다.")
  @Test
  void correctPage_WhenPageIsOverZero_ThenPageDoesNotChange() {
    // given
    final int page = 10;
    // when
    final int result = PaginationUtils.correctPage(page);
    // then
    assertThat(result).isEqualTo(10);
  }

  @DisplayName("0 보다 작은 값에 대한 페이지를 0으로 보정한다.")
  @Test
  void correctPage_WhenPageIsUnderZero_ThenPageChangeIntoZero() {
    // given
    final int page = -1;
    // when
    final int result = PaginationUtils.correctPage(page);
    // then
    assertThat(result).isEqualTo(0);
  }

  @DisplayName("null 값인 페이지를 0으로 보정한다.")
  @Test
  void correctPage_WhenPageIsNull_ThenPageChangeIntoZero() {
    // given
    final Integer page = null;
    // when
    final int result = PaginationUtils.correctPage(page);
    // then
    assertThat(result).isEqualTo(0);
  }

  @DisplayName("0보다 큰 값의 페이지 사이즈는 보정하지 않는다.")
  @Test
  void correctPage_WhenPageSizeIsOverZero_ThenPageSizeDoesNotChange() {
    // given
    final int size = 10;
    // when
    final int result = PaginationUtils.correctSize(size);
    // then
    assertThat(result).isEqualTo(10);
  }

  @DisplayName("0 보다 작은 값에 대한 페이지 사이즈를 기본값으로 보정한다.")
  @Test
  void correctPage_WhenPageSizeIsUnderZero_ThenPageSizeChangeIntoDefaultValue() {
    // given
    final int pageSize = -1;
    // when
    final int result = PaginationUtils.correctSize(pageSize);
    // then
    assertThat(result).isEqualTo(DEFAULT_PAGE_SIZE);
  }

  @DisplayName("null 값인 페이지 사이즈를 기본값으로 보정한다.")
  @Test
  void correctPage_WhenPageSizeIsNull_ThenPageSizeChangeIntoZero() {
    // given
    final Integer pageSize = null;
    // when
    final int result = PaginationUtils.correctSize(pageSize);
    // then
    assertThat(result).isEqualTo(DEFAULT_PAGE_SIZE);
  }

}