package kr.codesquad.issuetracker.label.domain;

import lombok.Getter;

@Getter
public class Label {

	private Integer id;
	private String name;
	private String description;
	private String fontColor;
	private String backgroundColor;
	private Boolean isDeleted;
}
