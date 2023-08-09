package kr.codesquad.issuetracker.presentation;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import kr.codesquad.issuetracker.application.MilestoneService;
import kr.codesquad.issuetracker.fixture.FixtureFactory;

@WebMvcTest(MilestoneController.class)
class MilestoneControllerTest extends ControllerTest {

	@MockBean
	private MilestoneService milestoneService;

	@DisplayName("마일스톤을 등록할 때 ")
	@Nested
	class MilestoneRegisterTest {

		@DisplayName("마일스톤 등록에 성공한다.")
		@Test
		void register() throws Exception {
			// given
			willDoNothing().given(milestoneService).register(anyString(), anyString(), any(LocalDateTime.class));

			// when & then
			mockMvc.perform(
					post("/api/milestones")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtProvider.createToken("1").getAccessToken())
						.content(objectMapper.writeValueAsBytes(
							FixtureFactory.createMilestoneCommonRequest("1주차 마일스톤"))))
				.andExpect(status().isCreated())
				.andDo(print());
		}

		@DisplayName("빈 마일스톤 이름이 들어오면 400응답을 한다.")
		@Test
		void givenInvalidRegisterInfo_thenResponse400() throws Exception {
			// given
			willDoNothing().given(milestoneService).register(anyString(), anyString(), any(LocalDateTime.class));

			// when & then
			mockMvc.perform(
					post("/api/milestones")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtProvider.createToken("1").getAccessToken())
						.content(objectMapper.writeValueAsString(FixtureFactory.createMilestoneCommonRequest(""))))
				.andExpect(status().isBadRequest())
				.andDo(print());
		}
	}

	@DisplayName("마일스톤을 수정할 때 ")
	@Nested
	class MilestoneModifyTest {

		@DisplayName("마일스톤 수정에 성공한다.")
		@Test
		void modify() throws Exception {
			// given
			willDoNothing().given(milestoneService)
				.modify(anyInt(), anyString(), anyString(), any(LocalDateTime.class));

			// when & then
			mockMvc.perform(
					put("/api/milestones/1")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtProvider.createToken("1").getAccessToken())
						.content(objectMapper.writeValueAsBytes(
							FixtureFactory.createMilestoneCommonRequest("1주차 마일스톤"))))
				.andExpect(status().isOk())
				.andDo(print());
		}

		@DisplayName("빈 마일스톤 이름이 들어오면 400응답을 한다.")
		@Test
		void givenInvalidRegisterInfo_thenResponse400() throws Exception {
			// given
			willDoNothing().given(milestoneService)
				.modify(anyInt(), anyString(), anyString(), any(LocalDateTime.class));

			// when & then
			mockMvc.perform(
					put("/api/milestones/1")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtProvider.createToken("1").getAccessToken())
						.content(objectMapper.writeValueAsString(FixtureFactory.createMilestoneCommonRequest(""))))
				.andExpect(status().isBadRequest())
				.andDo(print());
		}
	}
}
