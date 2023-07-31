package kr.codesquad.issuetracker.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.stream.Stream;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthAcceptanceTest {

	@Autowired
	private DatabaseInitializer databaseInitializer;

	@BeforeEach
	void setUp() {
		databaseInitializer.truncateTables();
		databaseInitializer.initTables();
	}

	@DisplayName("회원가입에 성공한다.")
	@Test
	void signupSuccess() {
		// given
		var given = RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(Map.of(
				"loginId", "bruni_login_id",
				"password", "asdf1234!"));

		// when
		var response = given
			.when().post("/api/auth/signup")
			.then().log().all()
			.extract();

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_CREATED);
	}

	@DisplayName("형식에 맞지 않는 회원가입 정보로 인해 회원가입에 실패한다.")
	@MethodSource("provideInvalidSignupInfo")
	@ParameterizedTest(name = "[{index}] 아이디: {0} 비밀번호: {1}")
	void givenInvalidSignupInfo_thenFailsSignup(String loginId, String password) {
		// given
		var given = RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(Map.of(
				"loginId", loginId,
				"password", password));

		// when
		var response = given
			.when().post("/api/auth/signup")
			.then().log().all()
			.extract();

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@DisplayName("중복된 로그인 아이디가 이미 존재해 회원가입에 실패한다.")
	@Test
	void givenDuplicatedLoginId_thenFailsSignup() {
		// given
		var given = RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(Map.of(
				"loginId", "iambruni",
				"password", "asdf123444!"));

		// when
		var response = given
			.when().post("/api/auth/signup")
			.then().log().all()
			.extract();

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	private static Stream<Arguments> provideInvalidSignupInfo() {
		return Stream.of(
			Arguments.of("short", "asdf1234!"),
			Arguments.of("not-short", "11111"),
			Arguments.of("toooooooooo long login-id", "1111111"),
			Arguments.of("brunii", "tooooooooooo long password")
		);
	}

	@DisplayName("로그인에 성공한다.")
	@Test
	void loginSuccess() {
		// given
		var given = RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(Map.of(
				"loginId", "iambruni",
				"password", "asdf1234"));

		// when
		var response = given
			.when().post("/api/auth/login")
			.then().log().all()
			.extract();

		// then
		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_CREATED),
			() -> assertThat(response.body().jsonPath().getString("tokenType")).isNotNull(),
			() -> assertThat(response.body().jsonPath().getString("accessToken")).isNotNull()
		);
	}

	@DisplayName("존재하지 않는 로그인 아이돌 인해 로그인에 실패한다.")
	@Test
	void givenNotExistsLoginId_thenFailsLogin() {
		// given
		var given = RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(Map.of(
				"loginId", "iamnotbruni",
				"password", "asdf1234"
			));

		// when
		var response = given
			.when().post("/api/auth/login")
			.then().log().all()
			.extract();

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND);
	}

	@DisplayName("비밀번호가 일치하지 않아 로그인에 실패한다.")
	@Test
	void givenNotMatchPassword_thenFailsLogin() {
		// given
		var given = RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(Map.of(
				"loginId", "iambruni",
				"password", "asdf12345"
			));

		// when
		var response = given
			.when().post("/api/auth/login")
			.then().log().all()
			.extract();

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}
}
