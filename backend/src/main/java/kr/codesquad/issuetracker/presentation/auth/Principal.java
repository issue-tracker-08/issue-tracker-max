package kr.codesquad.issuetracker.presentation.auth;

import java.util.Optional;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Principal {
	private static final String USER_ID = "userId";
	private static final String LOGIN_ID = "loginId";

	private Integer userId;
	private String loginId;

	public static Principal from(Claims claims) {
		final PrincipalBuilder principal = Principal.builder();
		Optional.ofNullable(claims.get(USER_ID))
			.ifPresent(userId -> principal.userId(Integer.valueOf(userId.toString())));
		Optional.ofNullable(claims.get(LOGIN_ID))
			.ifPresent(loginId -> principal.loginId(loginId.toString()));

		return principal.build();
	}
}
