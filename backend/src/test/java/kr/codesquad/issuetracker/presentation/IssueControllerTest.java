package kr.codesquad.issuetracker.presentation;

import static org.mockito.BDDMockito.*;
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
import org.springframework.http.MediaType;

import kr.codesquad.issuetracker.application.IssueService;
import kr.codesquad.issuetracker.fixture.FixtureFactory;
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
}
