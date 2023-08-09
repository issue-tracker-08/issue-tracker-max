package kr.codesquad.issuetracker.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import kr.codesquad.issuetracker.fixture.FixtureFactory;

public class IssueAcceptanceTest extends AcceptanceTest {

	@DisplayName("이슈 등록에 성공한다.")
	@Test
	void registerIssueSuccess() {
		// given
		var given = RestAssured
			.given().log().all()
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtProvider.createToken(Map.of("userId", "1")).getAccessToken())
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(FixtureFactory.createIssueRegisterRequest("테스트코드 작성하기", List.of(1, 2), List.of(1, 2)));

		// when
		var response = given
			.when()
			.post("/api/issues")
			.then().log().all()
			.extract();

		// then
		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(200),
			() -> assertThat(response.body().jsonPath().getInt("issueId")).isNotNull()
		);
	}
}
