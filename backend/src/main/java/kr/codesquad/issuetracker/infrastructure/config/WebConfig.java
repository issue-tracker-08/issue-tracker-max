package kr.codesquad.issuetracker.infrastructure.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.codesquad.issuetracker.presentation.auth.AuthPrincipalArgumentResolver;
import kr.codesquad.issuetracker.presentation.converter.OpenStateConverter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final AuthPrincipalArgumentResolver authPrincipalArgumentResolver;
	private final OpenStateConverter openStateConverter;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(authPrincipalArgumentResolver);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**")
			.allowedOrigins("http://issue-tracker-8.s3-website.ap-northeast-2.amazonaws.com", "http://localhost:5173",
				"https://fe-w4.d2kit8rai80g4y.amplifyapp.com/")
			.allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "DELETE", "PATCH");
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(openStateConverter);
	}
}
