package kr.codesquad.issuetracker.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.issuetracker.domain.UserAccount;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.infrastructure.persistence.UserAccountRepository;
import kr.codesquad.issuetracker.infrastructure.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final UserAccountRepository userAccountRepository;

	@Transactional
	public void signup(final String loginId, final String password) {
		if (userAccountRepository.existsByLoginId(loginId)) {
			throw new ApplicationException(ErrorCode.DUPLICATED_LOGIN_ID);
		}

		UserAccount userAccount = new UserAccount(loginId, passwordEncoder.encrypt(password));
		userAccountRepository.save(userAccount);
	}
}
