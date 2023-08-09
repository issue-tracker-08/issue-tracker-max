package kr.codesquad.issuetracker.presentation.auth;

import java.util.Optional;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Principal {

	private Integer userId;
	private String loginId;

	public static Principal from(Claims claims) {
		final PrincipalBuilder principal = Principal.builder();
		Optional.ofNullable(claims.get("userId"))
			.ifPresent(userId -> principal.userId(Integer.valueOf(userId.toString())));
		Optional.ofNullable(claims.get("loginId"))
			.ifPresent(loginId -> principal.loginId(loginId.toString()));

		return principal.build();
	}
}
