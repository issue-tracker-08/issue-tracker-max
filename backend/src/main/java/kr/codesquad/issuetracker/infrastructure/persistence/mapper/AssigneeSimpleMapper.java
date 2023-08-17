package kr.codesquad.issuetracker.infrastructure.persistence.mapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(value = AccessLevel.PRIVATE)
@Getter
public class AssigneeSimpleMapper {
	private Integer id;
	private String username;
	private String profileUrl;
}
