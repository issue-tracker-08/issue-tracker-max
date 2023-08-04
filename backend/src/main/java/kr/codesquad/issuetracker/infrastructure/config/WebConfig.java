package kr.codesquad.issuetracker.infrastructure.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.codesquad.issuetracker.presentation.auth.AuthPrincipalArgumentResolver;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final AuthPrincipalArgumentResolver authPrincipalArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(authPrincipalArgumentResolver);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**")
			.allowedOrigins("http://issue-tracker-8.s3-website.ap-northeast-2.amazonaws.com", "http://localhost:5173")
			.allowedMethods("*")
			.allowedHeaders("*");
	}
}
