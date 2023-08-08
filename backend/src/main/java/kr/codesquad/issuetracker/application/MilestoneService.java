package kr.codesquad.issuetracker.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.issuetracker.domain.Milestone;
import kr.codesquad.issuetracker.infrastructure.persistence.MilestoneRepository;
import kr.codesquad.issuetracker.presentation.response.MilestoneResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MilestoneService {

	private final MilestoneRepository milestoneRepository;

	@Transactional(readOnly = true)
	public List<MilestoneResponse> findAll() {
		return milestoneRepository.findAll();
	}

	@Transactional
	public void register(String name, String description, LocalDateTime dueDate) {
		Milestone milestone = new Milestone(name, description, dueDate);
		milestoneRepository.save(milestone);
	}
}

