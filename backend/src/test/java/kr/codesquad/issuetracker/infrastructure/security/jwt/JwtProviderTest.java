package kr.codesquad.issuetracker.infrastructure.security.jwt;

import static org.assertj.core.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.infrastructure.config.jwt.JwtProperties;

class JwtProviderTest {

	private final String secretKey = "1A3A06151A7154CA3ABD2CEF80F97769BBFC14FF69E9B5E61C75341255BDCE28";
	private final JwtProvider jwtProvider = new JwtProvider(new JwtProperties(secretKey, 3600000));

	@DisplayName("토큰 생성에 성공한다.")
	@Test
	void createTokenTest() {
		// given

		// when
		String token = jwtProvider.createToken("1");

		// then
		assertThat(token).isNotBlank();
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

	@DisplayName("토큰에서 userId를 추출한다.")
	@Test
	void givenToken_thenExtractUserId() {
		// given
		String token = jwtProvider.createToken("13");

		// when
		String userId = jwtProvider.extractUserId(token);

		// then
		assertThat(userId).isEqualTo("13");
	}
}
