package kr.codesquad.issuetracker.presentation;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import kr.codesquad.issuetracker.application.AuthService;
import kr.codesquad.issuetracker.fixture.FixtureFactory;
import kr.codesquad.issuetracker.presentation.response.TokenResponse;

@WebMvcTest(controllers = AuthController.class)
class AuthControllerTest extends ControllerTest {

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
						.content(objectMapper.writeValueAsString(
							FixtureFactory.createSignupRequest("applePIE", "qwerasdf"))))
				.andExpect(status().isCreated());
		}

		@DisplayName("형식에 맞지않는 회원가입 정보로 회원가입에 실패한다.")
		@Test
		void givenInvalidSignupInfo() throws Exception {
			mockMvc.perform(
					post("/api/auth/signup")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(
							FixtureFactory.createSignupRequest("aaa", "qwerasdf"))))
				.andExpect(status().isBadRequest());
		}
	}

	@Nested
	class LoginTest {

		@DisplayName("로그인에 성공한다.")
		@Test
		void login() throws Exception {
			String id = "applePIE";
			String pw = "qwer1234";

			given(authService.login(id, pw)).willReturn(new TokenResponse("qwerqwer"));

			mockMvc.perform(
					post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(
							objectMapper.writeValueAsString(FixtureFactory.createLoginRequest(id, pw))
						)
				).andExpect(status().isCreated())
				.andExpect(jsonPath("$.tokenType").exists())
				.andExpect(jsonPath("$.accessToken").exists());
		}
	}
}
