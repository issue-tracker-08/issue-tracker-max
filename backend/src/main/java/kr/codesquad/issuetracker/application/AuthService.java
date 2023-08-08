package kr.codesquad.issuetracker.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.issuetracker.domain.UserAccount;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.infrastructure.persistence.UserAccountRepository;
import kr.codesquad.issuetracker.infrastructure.security.hash.PasswordEncoder;
import kr.codesquad.issuetracker.infrastructure.security.jwt.JwtProvider;
import kr.codesquad.issuetracker.presentation.response.LoginSuccessResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

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

		LoginSuccessResponse.TokenResponse token = jwtProvider.createToken(String.valueOf(findUserAccount.getId()));
		return new LoginSuccessResponse(token, findUserAccount.getProfileUrl(), findUserAccount.getLoginId());
	}
}
