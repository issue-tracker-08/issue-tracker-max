package kr.codesquad.issuetracker.fixture;

import java.time.LocalDateTime;
import java.util.List;

import kr.codesquad.issuetracker.presentation.request.IssueRegisterRequest;
import kr.codesquad.issuetracker.presentation.request.LoginRequest;
import kr.codesquad.issuetracker.presentation.request.SignupRequest;
import kr.codesquad.issuetracker.presentation.response.IssueDetailResponse;
import kr.codesquad.issuetracker.presentation.response.LabelResponse;
import kr.codesquad.issuetracker.presentation.response.MilestoneResponse;

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

	public static IssueDetailResponse createIssueDetailResponse() {
		return new IssueDetailResponse(1, "이슈 제목", true, LocalDateTime.now(), "이슈 내용",
			new IssueDetailResponse.Author("작성자", "url"),
			List.of(new IssueDetailResponse.Assignee(2, "담당자", "url"),
				new IssueDetailResponse.Assignee(3, "담당자2", "url")),

			List.of(new LabelResponse(1, "feat", "#fff", "#ddd")),

			new MilestoneResponse(1, "BE Sprint #1", 3, 5));
	}
}
