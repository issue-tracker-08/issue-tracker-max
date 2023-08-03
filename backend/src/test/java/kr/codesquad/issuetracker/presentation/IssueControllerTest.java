package kr.codesquad.issuetracker.presentation;

import static kr.codesquad.issuetracker.presentation.request.IssueModifyRequest.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import kr.codesquad.issuetracker.application.IssueService;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.fixture.FixtureFactory;
import kr.codesquad.issuetracker.presentation.request.IssueModifyRequest;
import kr.codesquad.issuetracker.presentation.request.IssueRegisterRequest;

@WebMvcTest(IssueController.class)
class IssueControllerTest extends ControllerTest {

	@MockBean
	private IssueService issueService;

	@Nested
	class IssueRegisterTest {

		@DisplayName("이슈 등록에 성공한다.")
		@Test
		void issueRegister() throws Exception {
			// given
			IssueRegisterRequest request = FixtureFactory.createIssueRegisterRequest("프로젝트 세팅하기", List.of(1, 2),
				List.of(3, 4));
			given(issueService.register(anyInt(), any(IssueRegisterRequest.class))).willReturn(1);

			// when & then
			mockMvc.perform(
					post("/api/issues")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtProvider.createToken("1"))
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isFound())
				.andExpect(header().stringValues("Location", "/api/issues/1"))
				.andDo(print());
		}

		@DisplayName("권한이 없어 이슈 등록에 실패한다.")
		@Test
		void givenNoToken_thenResponse401() throws Exception {
			IssueRegisterRequest request = FixtureFactory.createIssueRegisterRequest("프로젝트 세팅하기", List.of(1, 2),
				List.of(3, 4));
			given(issueService.register(anyInt(), any(IssueRegisterRequest.class))).willReturn(1);

			// when & then
			mockMvc.perform(
					post("/api/issues")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isUnauthorized())
				.andDo(print());
		}

		@DisplayName("유효하지 않은 요청으로 인해 이슈 등록에 실패한다.")
		@Test
		void givenInvalidIssueRegisterRequest_thenResponse400() throws Exception {
			// given
			IssueRegisterRequest request = FixtureFactory.createIssueRegisterRequest("", List.of(1, 2), List.of(3, 4));
			given(issueService.register(anyInt(), any(IssueRegisterRequest.class))).willReturn(1);

			// when & then
			mockMvc.perform(
					post("/api/issues")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtProvider.createToken("1"))
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andDo(print());
		}
	}

	@Nested
	class IssueDetailTest {

		@DisplayName("이슈 상세 페이지 조회에 성공한다.")
		@Test
		void getIssueDetail() throws Exception {
			// given
			given(issueService.getIssueDetails(anyInt())).willReturn(FixtureFactory.createIssueDetailResponse());

			// when & then
			mockMvc.perform(
					request(HttpMethod.GET, "/api/issues/" + 1)
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtProvider.createToken("1")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.issueId").exists())
				.andExpect(jsonPath("$.title").exists())
				.andExpect(jsonPath("$.isOpen").exists())
				.andExpect(jsonPath("$.createdAt").exists())
				.andExpect(jsonPath("$.content").exists())
				.andExpect(jsonPath("$.author.username").exists())
				.andExpect(jsonPath("$.author.profileUrl").exists())
				.andExpect(jsonPath("$.assignees").exists())
				.andExpect(jsonPath("$.labels").exists())
				.andExpect(jsonPath("$.milestone").exists())
				.andDo(print());
		}

		@DisplayName("존재하지 않는 이슈 아이디가 주어지면 404 NOT FOUND를 응답한다.")
		@Test
		void givenNotExistsIssueId_thenResponse404() throws Exception {
			// given
			given(issueService.getIssueDetails(anyInt())).willThrow(
				new ApplicationException(ErrorCode.ISSUE_NOT_FOUND));

			// when & then
			mockMvc.perform(
					request(HttpMethod.GET, "/api/issues/" + 1)
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtProvider.createToken("1")))
				.andExpect(status().isNotFound())
				.andDo(print());
		}
	}

	@Nested
	class IssueModifyTest {

		@DisplayName("이슈 수정에 성공한다.")
		@Test
		void modifyIssue() throws Exception {
			// given
			willDoNothing().given(issueService).modifyIssue(anyInt(), anyInt(), any(IssueModifyRequest.class));

			// when & then
			mockMvc.perform(
					patch("/api/issues/1")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtProvider.createToken("1"))
						.content(objectMapper.writeValueAsString(
							FixtureFactory.createIssueModifyRequest("", null, null, UpdateProperty.TITLE))))
				.andExpect(status().isOk())
				.andDo(print());
		}

		@DisplayName("자신이 작성한 댓글이 아니라면 403응답을 한다.")
		@Test
		void givenNotAuthor_thenResponse403() throws Exception {
			// given
			willThrow(new ApplicationException(ErrorCode.NO_AUTHORIZATION))
				.given(issueService).modifyIssue(anyInt(), anyInt(), any(IssueModifyRequest.class));

			// when & then
			mockMvc.perform(
					patch("/api/issues/1")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtProvider.createToken("1"))
						.content(objectMapper.writeValueAsString(
							FixtureFactory.createIssueModifyRequest("", null, null, UpdateProperty.TITLE))))
				.andExpect(status().isForbidden())
				.andDo(print());
		}

		@DisplayName("유효한 형식의 요청이 아니라면 400응답을 한다.")
		@Test
		void givenInvalidRequest_thenResponse400() throws Exception {
			// given
			willDoNothing().given(issueService).modifyIssue(anyInt(), anyInt(), any(IssueModifyRequest.class));

			// when & then
			mockMvc.perform(
					patch("/api/issues/1")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtProvider.createToken("1"))
						.content("{\"data\": {\"title\": \"수정된 제목\",\"content\": null,\"isOpen\": null},"
							+ "\"updateProperty\": \"createdAt\"}"))
				.andExpect(status().isBadRequest())
				.andDo(print());
		}
	}
}
