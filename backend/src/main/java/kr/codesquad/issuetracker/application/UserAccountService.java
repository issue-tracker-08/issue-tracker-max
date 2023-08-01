package kr.codesquad.issuetracker.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.issuetracker.infrastructure.persistence.UserAccountRepository;
import kr.codesquad.issuetracker.presentation.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserAccountService {

	private final UserAccountRepository userAccountRepository;

	@Transactional(readOnly = true)
	public List<UserProfileResponse> findAll() {
		return userAccountRepository.findAll()
			.stream()
			.map(userAccount -> new UserProfileResponse(
				userAccount.getId(), userAccount.getLoginId(), userAccount.getProfileUrl()
			))
			.collect(Collectors.toList());
	}
}
