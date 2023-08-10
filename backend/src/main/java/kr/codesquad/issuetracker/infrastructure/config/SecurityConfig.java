package kr.codesquad.issuetracker.infrastructure.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.codesquad.config.JwtConfig;
import kr.codesquad.config.OAuthConfig;
import kr.codesquad.issuetracker.domain.AuthenticationContext;
import kr.codesquad.issuetracker.infrastructure.security.hash.PasswordEncoder;
import kr.codesquad.issuetracker.infrastructure.security.hash.SHA256;
import kr.codesquad.issuetracker.infrastructure.security.jwt.JwtProvider;
import kr.codesquad.issuetracker.presentation.filter.ExceptionHandlerFilter;
import kr.codesquad.issuetracker.presentation.filter.JwtFilter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Import({JwtConfig.class, OAuthConfig.class})
@Configuration
public class SecurityConfig {

	private final JwtProvider jwtProvider;
	private final AuthenticationContext authenticationContext;
	private final ObjectMapper objectMapper;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new SHA256();
	}

	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilter() {
		FilterRegistrationBean<JwtFilter> jwtFilter = new FilterRegistrationBean<>();
		jwtFilter.setFilter(new JwtFilter(jwtProvider, authenticationContext));
		jwtFilter.addUrlPatterns("/api/*");
		jwtFilter.setOrder(2);
		return jwtFilter;
	}

	@Bean
	public FilterRegistrationBean<ExceptionHandlerFilter> exceptionHandlerFilter() {
		FilterRegistrationBean<ExceptionHandlerFilter> exceptionHandlerFilter = new FilterRegistrationBean<>();
		exceptionHandlerFilter.setFilter(new ExceptionHandlerFilter(objectMapper));
		exceptionHandlerFilter.addUrlPatterns("/api/*");
		exceptionHandlerFilter.setOrder(1);
		return exceptionHandlerFilter;
	}
}
