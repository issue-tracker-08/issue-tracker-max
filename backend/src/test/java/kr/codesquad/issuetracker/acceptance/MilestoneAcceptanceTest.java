package kr.codesquad.issuetracker.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;

public class MilestoneAcceptanceTest extends AcceptanceTest {

	@DisplayName("마일스톤 등록에 성공한다.")
	@Test
	void register() {
		// given
		var given = RestAssured
			.given().log().all()
			.header(HttpHeaders.AUTHORIZATION,
				"Bearer " + jwtProvider.createToken(Map.of("userId", "1")).getAccessToken())
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(Map.of(
				"milestoneName", "BE 1주차 스프린트",
				"description", "화이팅!",
				"dueDate", "2023-09-01"));

		// when
		var response = given
			.when()
			.post("/api/milestones")
			.then().log().all()
			.extract();

		// then
		assertThat(response.statusCode()).isEqualTo(201);
	}

	@DisplayName("마일스톤 삭제에 성공한다.")
	@Test
	void remove() {
		// given
		var given = RestAssured
			.given().log().all()
			.header(HttpHeaders.AUTHORIZATION,
				"Bearer " + jwtProvider.createToken(Map.of("userId", "1")).getAccessToken())
			.contentType(MediaType.APPLICATION_JSON_VALUE);

		// when
		var response = given
			.when()
			.delete("/api/milestones/1")
			.then().log().all()
			.extract();

		// then
		assertThat(response.statusCode()).isEqualTo(204);
	}
}
