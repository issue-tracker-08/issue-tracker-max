package kr.codesquad.issuetracker.domain;

import lombok.Getter;

@Getter
public class Label {

	private Integer id;
	private String name;
	private String description;
	private String fontColor;
	private String backgroundColor;
	private Boolean isDeleted;

	public Label(Integer id, String name, String fontColor, String backgroundColor) {
		this.id = id;
		this.name = name;
		this.fontColor = fontColor;
		this.backgroundColor = backgroundColor;
	}
}
