package kr.codesquad.issuetracker.presentation.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OpenStateConverter implements Converter<String, OpenState> {

	@Override
	public OpenState convert(String openState) {
		return OpenState.of(openState);
	}
}
