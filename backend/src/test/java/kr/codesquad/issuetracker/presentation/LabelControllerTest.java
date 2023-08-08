package kr.codesquad.issuetracker.presentation;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import kr.codesquad.issuetracker.application.LabelService;
import kr.codesquad.issuetracker.presentation.request.LabelRequest;

@WebMvcTest(LabelController.class)
class LabelControllerTest extends ControllerTest {

	@MockBean
	private LabelService labelService;

	@DisplayName("레이블 생성에 성공한다.")
	@Test
	void labelRegister() throws Exception {
		// given
		LabelRequest request = new LabelRequest("feat", null, "1231", "black");
		willDoNothing().given(labelService).register(anyString(), anyString(), anyString(), anyString());

		// when & then
		mockMvc.perform(
				post("/api/labels")
					.contentType(MediaType.APPLICATION_JSON)
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtProvider.createToken("1").getAccessToken())
					.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated());
	}

}
