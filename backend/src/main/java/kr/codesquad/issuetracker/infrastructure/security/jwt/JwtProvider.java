package kr.codesquad.issuetracker.infrastructure.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.infrastructure.config.jwt.JwtProperties;

@Component
public class JwtProvider {

	private final SecretKey secretKey;
	private final long expirationMilliseconds;

	public JwtProvider(JwtProperties jwt) {
		this.secretKey = Keys.hmacShaKeyFor(jwt.getSecretKey().getBytes(StandardCharsets.UTF_8));
		this.expirationMilliseconds = jwt.getExpirationMilliseconds();
	}

	public String createToken(String payload) {
		Date now = new Date();
		return Jwts.builder()
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + expirationMilliseconds))
			.setClaims(Map.of("userId", payload))
			.compact();
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

	public String extractUserId(final String token) {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.get("userId")
			.toString();
	}
}
