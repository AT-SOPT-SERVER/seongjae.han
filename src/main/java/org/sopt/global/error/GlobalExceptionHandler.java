package org.sopt.global.error;

import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.global.response.ApiResponse;
import org.sopt.global.util.log.LoggingUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private final LoggingUtil loggingUtil;

  public GlobalExceptionHandler(final LoggingUtil loggingUtil) {
    this.loggingUtil = loggingUtil;
  }

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiResponse<Void>> handleAPIException(ApiException exception) {

    ApiResponse<Void> apiResponse = ApiResponse.error(exception.getErrorCode().getCode(),
        exception.getMessage());

    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
  }

  // 2. @Valid 검증 실패
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      org.springframework.web.bind.MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatusCode status, WebRequest request) {

    loggingUtil.error(ex);

    String firstErrorMsg = ex.getBindingResult().getAllErrors().stream()
        .findFirst()
        .map(org.springframework.validation.ObjectError::getDefaultMessage)
        .orElse("유효성 검사 실패");

    ApiResponse<Void> apiResponse = ApiResponse.error(
        ErrorCode.CLIENT_BAD_REQUEST.getCode(), firstErrorMsg);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
  }

  // 3. JSON 파싱/Enum 매핑 에러 등
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

    loggingUtil.error(ex);
    ApiResponse<Void> apiResponse = ApiResponse.error(
        ErrorCode.CLIENT_BAD_REQUEST.getCode(), "요청 데이터 형식이 잘못되었습니다.");

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
  }

  // 4. 파라미터 바인딩 등 (ex: 쿼리파라미터 타입 안맞음)
  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
      org.springframework.beans.TypeMismatchException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {

    loggingUtil.error(ex);
    ApiResponse<Void> apiResponse = ApiResponse.error(
        ErrorCode.CLIENT_BAD_REQUEST.getCode(), "파라미터 형식이 잘못되었습니다.");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleAllUncaughtException(Exception exception) {
    loggingUtil.error(exception);

    ApiResponse<Void> apiResponse = ApiResponse.error(
        ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
        exception.getMessage()
    );

    // 진짜 500을 리턴하려면 500으로!
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
  }
}
