package kr.codesquad.issuetracker.domain;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.Getter;

@Component
@Getter
@RequestScope
public class AuthenticationContext {

	private String principal;

	public void setPrincipal(String principal) {
		this.principal = principal;
	}
}
