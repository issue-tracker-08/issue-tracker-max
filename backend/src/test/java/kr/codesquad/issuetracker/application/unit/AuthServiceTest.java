package kr.codesquad.issuetracker.application.unit;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.codesquad.issuetracker.application.AuthService;
import kr.codesquad.issuetracker.domain.UserAccount;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.infrastructure.persistence.UserAccountRepository;
import kr.codesquad.issuetracker.infrastructure.security.hash.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@Mock
	private UserAccountRepository userAccountRepository;
	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private AuthService authService;

	@DisplayName("회원가입할 때")
	@Nested
	class SignupTest {

		@DisplayName("올바른 회원가입 정보가 주어지면 회원가입에 성공한다.")
		@Test
		void signUpTest() {
			// given
			given(userAccountRepository.existsByLoginId(anyString())).willReturn(Boolean.FALSE);

			// when & then
			assertThatCode(() -> authService.signup("bruniii", "asdf1234!"))
				.doesNotThrowAnyException();
		}

		@DisplayName("중복된 로그인 아이디가 주어지면 회원가입에 실패한다.")
		@Test
		void duplicatedLoginIdSignupTest() {
			// given
			given(userAccountRepository.existsByLoginId(anyString())).willReturn(Boolean.TRUE);

			// when & then
			assertAll(
				() -> assertThatThrownBy(() -> authService.signup("bruniii", "asdf1234!"))
					.isInstanceOf(ApplicationException.class)
					.extracting("errorCode").isEqualTo(ErrorCode.DUPLICATED_LOGIN_ID),
				() -> then(userAccountRepository).should(never()).save(any(UserAccount.class))
			);
		}

		@DisplayName("존재하지 않는 아이디로 로그인 시 예외가 발생한다.")
		@Test
		void notExistLoginId() {
			// given
			given(userAccountRepository.findByLoginId(anyString())).willReturn(Optional.empty());

			// when & then
			assertThatThrownBy(() -> authService.login("applePIE", "12341234"))
				.isInstanceOf(ApplicationException.class)
				.extracting("errorCode").isEqualTo(ErrorCode.NOT_FOUND);
		}
	}
}
