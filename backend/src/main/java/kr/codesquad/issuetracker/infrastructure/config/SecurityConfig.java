package kr.codesquad.issuetracker.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.codesquad.issuetracker.infrastructure.security.PasswordEncoder;
import kr.codesquad.issuetracker.infrastructure.security.SHA256;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new SHA256();
	}
}
