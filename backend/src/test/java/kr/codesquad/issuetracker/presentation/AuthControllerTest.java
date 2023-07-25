package kr.codesquad.issuetracker.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.codesquad.issuetracker.application.AuthService;
import kr.codesquad.issuetracker.fixture.FixtureFactory;

@WebMvcTest(controllers = AuthController.class)
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	AuthService authService;

	@Nested
	class SignupTest {

		@DisplayName("회원가입에 성공한다.")
		@Test
		void signup() throws Exception {
			mockMvc.perform(
					post("/api/auth/signup")
						.contentType(MediaType.APPLICATION_JSON)
						.content(
							objectMapper.writeValueAsString(FixtureFactory.createSignupRequest("applePIE", "qwerasdf"))))
				.andExpect(status().isCreated());
		}

		@DisplayName("형식에 맞지않는 회원가입 정보로 회원가입에 실패한다.")
		@Test
		void givenInvalidSignupInfo() throws Exception {
			mockMvc.perform(
					post("/api/auth/signup")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(FixtureFactory.createSignupRequest("aaa", "qwerasdf"))))
				.andExpect(status().isBadRequest());
		}
	}
}
