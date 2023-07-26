package kr.codesquad.issuetracker.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;
import java.util.stream.Stream;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthAcceptanceTest {

	@Autowired
	private DatabaseInitializer databaseInitializer;

	@BeforeEach
	void setUp() {
		databaseInitializer.truncateTables();
		databaseInitializer.initTables();
	}

	@Test
	void 회원가입을_한다() {
		// given
		var given = 올바른_회원가입_정보가_주어지면();

		// when
		var response = given
			.when().post("/api/auth/signup")
			.then().log().all()
			.extract();

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_CREATED);
	}

	@MethodSource("provideInvalidSignupInfo")
	@ParameterizedTest(name = "[{index}] 아이디: {0} 비밀번호: {1}")
	void 잘못된_형식으로_회원가입을_실패한다(String loginId, String password) {
		// given
		var given = 잘못된_회원가입_정보가_주어지면(loginId, password);

		// when
		var response = given
			.when().post("/api/auth/signup")
			.then().log().all()
			.extract();

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	void 아이디가_이미_존재해_회원가입에_실패한다() {
		// given
		var given = 중복된_아이디의_회원가입_정보가_주어지면();

		// when
		var response = given
			.when().post("/api/auth/signup")
			.then().log().all()
			.extract();

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	private RequestSpecification 올바른_회원가입_정보가_주어지면() {
		return RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(Map.of(
				"loginId", "bruni_login_id",
				"password", "asdf1234!"));
	}

	private RequestSpecification 잘못된_회원가입_정보가_주어지면(String loginId, String password) {
		return RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(Map.of(
				"loginId", loginId,
				"password", password));
	}

	private static Stream<Arguments> provideInvalidSignupInfo() {
		return Stream.of(
			Arguments.of("short", "asdf1234!"),
			Arguments.of("not-short", "11111"),
			Arguments.of("toooooooooo long login-id", "1111111"),
			Arguments.of("brunii", "tooooooooooo long password")
		);
	}

	private RequestSpecification 중복된_아이디의_회원가입_정보가_주어지면() {
		return RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(Map.of(
				"loginId", "iambruni",
				"password", "asdf123444!"));
	}
}
