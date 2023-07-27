package kr.codesquad.issuetracker.infrastructure.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import kr.codesquad.config.JwtConfig;
import kr.codesquad.issuetracker.infrastructure.security.hash.PasswordEncoder;
import kr.codesquad.issuetracker.infrastructure.security.hash.SHA256;
import kr.codesquad.issuetracker.infrastructure.security.jwt.JwtProvider;
import kr.codesquad.issuetracker.presentation.filter.JwtFilter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Import(JwtConfig.class)
@Configuration
public class SecurityConfig {

	private final JwtProvider jwtProvider;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new SHA256();
	}

	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilter() {
		FilterRegistrationBean<JwtFilter> jwtFilter = new FilterRegistrationBean<>();
		jwtFilter.setFilter(new JwtFilter(jwtProvider));
		jwtFilter.addUrlPatterns("/api/*");
		return jwtFilter;
	}
}
