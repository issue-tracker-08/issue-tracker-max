package kr.codesquad.issuetracker.infrastructure.persistence.mapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(value = AccessLevel.PRIVATE)
@Getter
public class LabelSimpleMapper {
	private Integer id;
	private String name;
	private String fontColor;
	private String backgroundColor;
}
