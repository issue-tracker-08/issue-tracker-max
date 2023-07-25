package kr.codesquad.issuetracker.infrastructure.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 implements PasswordEncoder {

	@Override
	public String encrypt(final String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(text.getBytes());
			return bytesToHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException();
		}
	}

	private String bytesToHex(byte[] bytes) {
		StringBuilder hexBuilder = new StringBuilder();
		for (byte b : bytes) {
			hexBuilder.append(String.format("%02x", b));
		}
		return hexBuilder.toString();
	}
}

