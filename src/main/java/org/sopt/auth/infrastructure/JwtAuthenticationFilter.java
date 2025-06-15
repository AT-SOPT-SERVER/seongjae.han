package org.sopt.auth.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.global.constants.AppConstants;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.global.response.ApiResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {

  private final JwtProvider jwtProvider;
  private final ObjectMapper objectMapper;

  @Override
  public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
      final FilterChain filterChain) throws IOException, ServletException {
    final HttpServletRequest request = (HttpServletRequest) servletRequest;
    final HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

    try {
      final String path = request.getRequestURI();

      List<String> excludedPaths = getExcludedPaths();
      if (excludedPaths.contains(path)) {
        filterChain.doFilter(servletRequest, servletResponse);
        return;
      }

      String token = resolveToken(request);

      if (token == null) {
        handleUnauthorized(httpServletResponse);
        return;
      }

      Long userId = jwtProvider.getUserId(token);
      AuthContext.setCurrentUserId(userId);

      filterChain.doFilter(servletRequest, servletResponse);
    } catch (ApiException e) {
      handleErrorResponse(httpServletResponse, e.getErrorCode());
    } catch (Exception e) {
      handleUnauthorized(httpServletResponse);
    } finally {
      AuthContext.clear();
    }

  }

  private static List<String> getExcludedPaths() {
    return List.of("/api/v1/auth/login", "/api/v1/user/signup");
  }

  private String resolveToken(HttpServletRequest request) {
    String bearer = request.getHeader(AppConstants.JWT_HEADER);
    if (bearer != null && bearer.startsWith(AppConstants.TOKEN_PREFIX)) {
      return bearer.substring(7);
    }
    return null;
  }

  private void handleUnauthorized(HttpServletResponse response) throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json; charset=UTF-8");

    final ApiResponse<Void> baseResponse = ApiResponse.error(ErrorCode.USER_UNAUTHORIZED);

    String json = objectMapper.writeValueAsString(baseResponse);

    response.getWriter().write(json);
  }

  private void handleErrorResponse(HttpServletResponse response, ErrorCode errorCode)
      throws IOException {
    response.setStatus(errorCode.getStatus().value()); // ex: 401, 403 등
    response.setContentType("application/json; charset=UTF-8");
    final ApiResponse<Void> baseResponse = ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR);

    String json = objectMapper.writeValueAsString(baseResponse);

    response.getWriter().write(json);
  }
}
