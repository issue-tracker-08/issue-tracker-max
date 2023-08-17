package kr.codesquad.issuetracker.infrastructure.persistence.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AssigneeSimpleMapper {
	private Integer id;
	private String username;
	private String profileUrl;
}
