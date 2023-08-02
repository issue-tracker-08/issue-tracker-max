package kr.codesquad.issuetracker.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.codesquad.issuetracker.ApplicationTest;
import kr.codesquad.issuetracker.domain.UserAccount;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.infrastructure.persistence.UserAccountRepository;
import kr.codesquad.issuetracker.infrastructure.security.hash.PasswordEncoder;
import kr.codesquad.issuetracker.presentation.response.TokenResponse;

@ApplicationTest
public class AuthServiceTest {

	@Autowired
	private AuthService authService;
	@Autowired
	private UserAccountRepository userAccountRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@DisplayName("회원가입할 때")
	@Nested
	class SignupTest {

		@DisplayName("회원가입에 성공한다.")
		@Test
		void signupSuccess() {
			// given

			// when
			authService.signup("login_id", "password");

			// then
			assertThat(userAccountRepository.existsByLoginId("login_id")).isTrue();
		}

		@DisplayName("중복된 로그인 아이디로 회원가입을 시도하면 예외를 던진다.")
		@Test
		void givenDuplicatedLoginId_thenThrowsException() {
			// given
			userAccountRepository.save(new UserAccount("loginId", "password"));

			// when & then
			assertThatThrownBy(() -> authService.signup("loginId", "new_password"))
				.isInstanceOf(ApplicationException.class)
				.extracting("errorCode").isEqualTo(ErrorCode.DUPLICATED_LOGIN_ID);
		}
	}

	@DisplayName("로그인할 때 ")
	@Nested
	class LoginTest {

		@DisplayName("로그인에 성공한다.")
		@Test
		void loginSuccess() {
			// given
			userAccountRepository.save(new UserAccount("loginId", passwordEncoder.encrypt("password")));

			// when
			TokenResponse response = authService.login("loginId", "password");

			// then
			assertAll(
				() -> assertThat(response).isNotNull(),
				() -> assertThat(response.getTokenType()).isEqualTo("Bearer"),
				() -> assertThat(response.getAccessToken()).isNotEmpty()
			);
		}

		@DisplayName("잘못된 비밀번호가 주어지면 예외를 던진다.")
		@Test
		void givenInvalidPassword_thenThrowsException() {
			// given
			userAccountRepository.save(new UserAccount("loginId", passwordEncoder.encrypt("password")));

			// when & then
			assertThatThrownBy(() -> authService.login("loginId", "not_password"))
				.isInstanceOf(ApplicationException.class)
				.extracting("errorCode").isEqualTo(ErrorCode.FAILED_LOGIN);
		}

		@DisplayName("존재하지 않는 로그인 아이디가 주어지면 예외를 던진다.")
		@Test
		void givenNotExistsLoginId_thenThrowsException() {
			// given

			// when & then
			assertThatThrownBy(() -> authService.login("loginId", "password"))
				.isInstanceOf(ApplicationException.class)
				.extracting("errorCode").isEqualTo(ErrorCode.NOT_FOUND);
		}
	}
}
