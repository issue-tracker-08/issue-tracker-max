package kr.codesquad.issuetracker.application;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.issuetracker.domain.GithubUser;
import kr.codesquad.issuetracker.domain.UserAccount;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.exception.InitialLoginException;
import kr.codesquad.issuetracker.infrastructure.persistence.UserAccountRepository;
import kr.codesquad.issuetracker.infrastructure.security.hash.PasswordEncoder;
import kr.codesquad.issuetracker.infrastructure.security.jwt.JwtProvider;
import kr.codesquad.issuetracker.infrastructure.security.oauth.GithubClient;
import kr.codesquad.issuetracker.presentation.response.LoginSuccessResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

	private final GithubClient githubClient;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	private final UserAccountRepository userAccountRepository;

	@Transactional
	public void signup(final String loginId, final String password) {
		if (userAccountRepository.existsByLoginId(loginId)) {
			throw new ApplicationException(ErrorCode.DUPLICATED_LOGIN_ID);
		}

		UserAccount userAccount = new UserAccount(loginId, passwordEncoder.encrypt(password));
		userAccountRepository.save(userAccount);
	}

	@Transactional(readOnly = true)
	public LoginSuccessResponse login(final String loginId, final String password) {
		UserAccount findUserAccount = userAccountRepository.findByLoginId(loginId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

		if (!findUserAccount.isSamePassword(passwordEncoder.encrypt(password))) {
			throw new ApplicationException(ErrorCode.FAILED_LOGIN);
		}

		LoginSuccessResponse.TokenResponse token = jwtProvider.createToken(Map.of(
			"userId", String.valueOf(findUserAccount.getId()),
			"loginId", loginId
		));

		return new LoginSuccessResponse(token, findUserAccount.getProfileUrl(), findUserAccount.getLoginId());
	}

	@Transactional(readOnly = true)
	public LoginSuccessResponse oauthLogin(final String code) {
		GithubUser oAuthUser = githubClient.getOAuthUser(code);
		String email = oAuthUser.getEmail();

		UserAccount userAccount = userAccountRepository.findByEmail(email)  // 최초로그인의 경우 email 응답
			.orElseThrow(() -> new InitialLoginException("최초 로그인입니다.", email));

		LoginSuccessResponse.TokenResponse token = jwtProvider.createToken(Map.of(
			"userId", String.valueOf(userAccount.getId()),
			"loginId", userAccount.getLoginId()
		));

		return new LoginSuccessResponse(token, userAccount.getProfileUrl(), userAccount.getLoginId());
	}

	@Transactional
	public LoginSuccessResponse oauthSignup(String email, String username) {
		if (userAccountRepository.existsByLoginId(username)) {
			throw new ApplicationException(ErrorCode.DUPLICATED_LOGIN_ID);
		}
		UserAccount userAccount = UserAccount.fromOAuthData(username, email);
		int id = userAccountRepository.save(userAccount);

		LoginSuccessResponse.TokenResponse token = jwtProvider.createToken(Map.of(
			"userId", String.valueOf(id),
			"loginId", userAccount.getLoginId()
		));

		return new LoginSuccessResponse(token, userAccount.getProfileUrl(), userAccount.getLoginId());
	}

	public String getOAuthLoginPageUrl() {
		return new StringBuilder().append(githubClient.getGithubLoginBaseUrl())
			.append("/authorize")
			.append("?client_id=").append(githubClient.getClientId())
			.append("&scope=user:email").toString();
	}
}
