package kr.codesquad.issuetracker.presentation.request;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import kr.codesquad.issuetracker.domain.Issue;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueModifyRequest {

	private ModifyData data;
	private UpdateProperty updateProperty;

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ModifyData {

		private String title;
		private String content;
		private Boolean isOpen;
	}

	@RequiredArgsConstructor
	public enum UpdateProperty {
		TITLE("title"), CONTENT("content"), IS_OPEN("isOpen");

		private final String name;

		@JsonCreator
		public static UpdateProperty fromString(String updateProperty) {
			return Arrays.stream(UpdateProperty.values())
				.filter(property -> property.name.equals(updateProperty))
				.findFirst()
				.orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_INPUT));
		}

		@JsonValue
		public String getName() {
			return name;
		}
	}

	public void modifyData(Issue issue) {
		if (updateProperty == UpdateProperty.TITLE) {
			issue.modifyTitle(data.title);
			return;
		}
		if (updateProperty == UpdateProperty.CONTENT) {
			issue.modifyContent(data.content);
			return;
		}
		if (updateProperty == UpdateProperty.IS_OPEN) {
			issue.modifyOpenStatus(data.isOpen);
		}
	}
}
