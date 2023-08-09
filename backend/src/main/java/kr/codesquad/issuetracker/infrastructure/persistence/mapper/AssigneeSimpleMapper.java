package kr.codesquad.issuetracker.infrastructure.persistence.mapper;

import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AssigneeSimpleMapper {
	private String username;
	private String profileUrl;

	public boolean hasValue() {
			return StringUtils.hasText(username);
		}
}
