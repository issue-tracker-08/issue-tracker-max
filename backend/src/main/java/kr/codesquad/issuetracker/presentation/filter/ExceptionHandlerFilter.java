package kr.codesquad.issuetracker.presentation.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			filterChain.doFilter(request, response);
		} catch (ApplicationException e) {
			setErrorResponse(e.getErrorCode(), response);
		}
	}

	private void setErrorResponse(ErrorCode errorcode, HttpServletResponse response) throws
		IOException {
		response.setStatus(errorcode.getStatusCode());
		response.setContentType("application/json;charset=UTF-8");
		ErrorResponse errorResponse = new ErrorResponse(errorcode);
		response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
	}
}
