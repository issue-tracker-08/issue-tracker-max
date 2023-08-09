package kr.codesquad.issuetracker.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.issuetracker.domain.Label;
import kr.codesquad.issuetracker.infrastructure.persistence.LabelRepository;
import kr.codesquad.issuetracker.presentation.response.LabelResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LabelService {

	private final LabelRepository labelRepository;

	@Transactional(readOnly = true)
	public List<LabelResponse> findAll() {
		return labelRepository.findAll().stream()
			.map(label -> new LabelResponse(
				label.getId(),
				label.getName(),
				label.getDescription(),
				label.getFontColor(),
				label.getBackgroundColor()
			))
			.collect(Collectors.toList());
	}

	@Transactional
	public void register(String name, String description, String frontColor, String backgroundColor) {
		Label label = new Label(name, description, frontColor, backgroundColor);
		labelRepository.save(label);
	}
}
