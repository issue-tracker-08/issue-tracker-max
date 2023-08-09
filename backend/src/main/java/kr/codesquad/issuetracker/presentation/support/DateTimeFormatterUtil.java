package kr.codesquad.issuetracker.presentation.support;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeFormatterUtil {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private DateTimeFormatterUtil() {
	}

	public static String toString(LocalDateTime localDateTime) {
		return formatter.format(localDateTime);
	}
}
