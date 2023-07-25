package kr.codesquad.issuetracker.infrastructure.security;

public interface PasswordEncoder {

	String encrypt(final String text);
}
