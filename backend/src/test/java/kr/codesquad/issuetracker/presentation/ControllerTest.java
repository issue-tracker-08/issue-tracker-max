package kr.codesquad.issuetracker.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.codesquad.issuetracker.domain.AuthenticationContext;
import kr.codesquad.issuetracker.infrastructure.config.SecurityConfig;
import kr.codesquad.issuetracker.infrastructure.config.WebConfig;
import kr.codesquad.issuetracker.infrastructure.security.jwt.JwtProvider;
import kr.codesquad.issuetracker.presentation.filter.ExceptionHandlerFilter;
import kr.codesquad.issuetracker.presentation.filter.JwtFilter;

@Import({WebConfig.class, SecurityConfig.class, JwtProvider.class, AuthenticationContext.class})
@WebMvcTest
public class ControllerTest {

	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	protected ObjectMapper objectMapper;
	@Autowired
	protected FilterRegistrationBean<JwtFilter> jwtFilter;
	@Autowired
	protected FilterRegistrationBean<ExceptionHandlerFilter> exceptionHandlerFilter;
	@Autowired
	protected JwtProvider jwtProvider;

	@BeforeEach
	void setUp(WebApplicationContext context) {
		mockMvc = MockMvcBuilders
			.webAppContextSetup(context)
			.addFilter(exceptionHandlerFilter.getFilter())
			.addFilter(jwtFilter.getFilter())
			.build();
	}
}
