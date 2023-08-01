package kr.codesquad.issuetracker.fixture;

import java.util.List;

import kr.codesquad.issuetracker.presentation.request.IssueRegisterRequest;
import kr.codesquad.issuetracker.presentation.request.LoginRequest;
import kr.codesquad.issuetracker.presentation.request.SignupRequest;

public class FixtureFactory {

	public static SignupRequest createSignupRequest(String id, String pw) {
		return new SignupRequest(id, pw);
	}

	public static LoginRequest createLoginRequest(String id, String pw) {
		return new LoginRequest(id, pw);
	}

	public static IssueRegisterRequest createIssueRegisterRequest(String title,
		List<Integer> assigneeIds, List<Integer> labelIds) {
		return new IssueRegisterRequest(
			title,
			"프로젝트를 잘 설정해봅시다.",
			assigneeIds,
			labelIds,
			1
		);
	}
}
