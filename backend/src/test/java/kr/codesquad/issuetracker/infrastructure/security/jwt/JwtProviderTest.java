package kr.codesquad.issuetracker.infrastructure.security.jwt;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.infrastructure.config.jwt.JwtProperties;
import kr.codesquad.issuetracker.presentation.auth.Principal;
import kr.codesquad.issuetracker.presentation.response.LoginSuccessResponse;

class JwtProviderTest {

	private final String secretKey = "1A3A06151A7154CA3ABD2CEF80F97769BBFC14FF69E9B5E61C75341255BDCE28";
	private final JwtProvider jwtProvider = new JwtProvider(new JwtProperties(secretKey, 3600000));

	@DisplayName("토큰 생성에 성공한다.")
	@Test
	void createTokenTest() {
		// given

		// when
		LoginSuccessResponse.TokenResponse token = jwtProvider.createToken(Map.of("userId", "1"));

		// then
		assertThat(token.getAccessToken()).isNotBlank();
	}

	@DisplayName("유효하지 않은 토큰이면 예외가 발생한다.")
	@Test
	void givenInvalidToken_thenThrowsException() {
		// given
		String invalidToken = "asdfasdf.asdfasdf.asdfasfd";

		// when & then
		assertThatThrownBy(() -> jwtProvider.validateToken(invalidToken))
			.isInstanceOf(ApplicationException.class)
			.extracting("errorCode").isEqualTo(ErrorCode.INVALID_JWT);
	}

	@DisplayName("만료된 토큰이면 예외가 발생한다.")
	@Test
	void givenExpiredToken_thenThrowsException() {
		// given
		Date now = new Date();
		String token = Jwts.builder()
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() - 1))
			.signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
			.compact();

		// when & then
		assertThatThrownBy(() -> jwtProvider.validateToken(token))
			.isInstanceOf(ApplicationException.class)
			.extracting("errorCode").isEqualTo(ErrorCode.EXPIRED_JWT);
	}

	@DisplayName("토큰에서 유저 정보가 담긴 Principal을 추출한다.")
	@Test
	void givenToken_thenExtractUserId() {
		// given
		LoginSuccessResponse.TokenResponse token = jwtProvider.createToken(Map.of(
			"userId", "13",
			"loginId", "bean1234"
		));

		// when
		Principal principal = jwtProvider.extractPrincipal(token.getAccessToken());

		// then
		assertAll(
			() -> assertThat(principal.getUserId()).isEqualTo(13),
			() -> assertThat(principal.getLoginId()).isEqualTo("bean1234")
		);
	}
}
