package kr.codesquad.issuetracker.infrastructure.persistence.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorMapper {
	private Integer id;
	private String username;
	private String profileUrl;
}
