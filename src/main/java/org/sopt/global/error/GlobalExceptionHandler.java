package org.sopt.global.error;

import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.global.response.ApiResponse;
import org.sopt.global.util.log.LoggingUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
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

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex,
      final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
    this.loggingUtil.error(ex);

    ApiResponse<Void> apiResponse = ApiResponse.error(
        ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
        ErrorCode.INTERNAL_SERVER_ERROR.getMessage()
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
  }
}
