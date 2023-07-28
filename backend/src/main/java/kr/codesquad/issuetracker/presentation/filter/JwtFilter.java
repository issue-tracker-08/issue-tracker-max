package kr.codesquad.issuetracker.presentation.filter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.infrastructure.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private static final String AUTHORIZATION = "Authorization";
	private static final String BEARER = "bearer";
	private static final int TOKEN_INDEX = 1;

	private static final AntPathMatcher pathMatcher = new AntPathMatcher();
	private static final List<String> excludeUrlPatterns = List.of("/api/auth/**");

	private final JwtProvider jwtProvider;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return excludeUrlPatterns.stream()
			.anyMatch(pattern -> pathMatcher.match(pattern, request.getServletPath()));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		extractJwt(request).ifPresentOrElse(jwtProvider::validateToken, () -> {
			throw new ApplicationException(ErrorCode.EMPTY_JWT);
		});

		filterChain.doFilter(request, response);
	}

	private Optional<String> extractJwt(HttpServletRequest request) {
		String header = request.getHeader(AUTHORIZATION);

		if (!StringUtils.hasText(header) || !header.toLowerCase().startsWith(BEARER)) {
			return Optional.empty();
		}

		return Optional.of(header.split(" ")[TOKEN_INDEX]);
	}
}
