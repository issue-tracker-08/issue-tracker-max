package kr.codesquad.issuetracker.domain;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import kr.codesquad.issuetracker.presentation.auth.Principal;
import lombok.Getter;

@Component
@Getter
@RequestScope
public class AuthenticationContext {

	private Principal principal;

	public void setPrincipal(Principal principal) {
		this.principal = principal;
	}
}
