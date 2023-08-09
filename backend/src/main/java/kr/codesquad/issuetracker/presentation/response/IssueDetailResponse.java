package kr.codesquad.issuetracker.presentation.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueDetailResponse {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private Integer issueId;
	private String title;
	private Boolean isOpen;
	private String createdAt;
	private String content;
	private Author author;
	private Integer commentCount;

	public IssueDetailResponse(Integer issueId, String title, Boolean isOpen, LocalDateTime createdAt, String content,
		Author author,
		Integer commentCount) {
		this.issueId = issueId;
		this.title = title;
		this.isOpen = isOpen;
		this.createdAt = formatter.format(createdAt);
		this.content = content;
		this.author = author;
		this.commentCount = commentCount;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Author {

		private String username;
		private String profileUrl;
	}
}
