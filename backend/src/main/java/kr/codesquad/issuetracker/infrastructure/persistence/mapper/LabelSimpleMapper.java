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
public class LabelSimpleMapper {
	private Integer id;
	private String name;
	private String fontColor;
	private String backgroundColor;
}
