package kr.codesquad.issuetracker.presentation.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentsResponse {

	private Integer id;
	private String username;
	private String profileUrl;
	private String content;
	private LocalDateTime createdAt;
}
