package kr.codesquad.issuetracker.infrastructure.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.infrastructure.config.jwt.JwtProperties;
import kr.codesquad.issuetracker.presentation.auth.Principal;
import kr.codesquad.issuetracker.presentation.response.LoginSuccessResponse;

@Component
public class JwtProvider {

	private final SecretKey secretKey;
	private final long expirationMilliseconds;

	public JwtProvider(JwtProperties jwt) {
		this.secretKey = Keys.hmacShaKeyFor(jwt.getSecretKey().getBytes(StandardCharsets.UTF_8));
		this.expirationMilliseconds = jwt.getExpirationMilliseconds();
	}

	public LoginSuccessResponse.TokenResponse createToken(final Map<String, String> payloads) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + expirationMilliseconds);
		long expirationMilli = expiration.toInstant().toEpochMilli();

		String token = Jwts.builder()
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.setIssuedAt(now)
			.setExpiration(expiration)
			.setClaims(payloads)
			.compact();
		return new LoginSuccessResponse.TokenResponse(token, expirationMilli);
	}

	public void validateToken(final String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			throw new ApplicationException(ErrorCode.EXPIRED_JWT);
		} catch (JwtException e) {
			throw new ApplicationException(ErrorCode.INVALID_JWT);
		}
	}

	public Principal extractPrincipal(final String token) {
		final Claims claims = Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody();

		return Principal.from(claims);
	}
}
