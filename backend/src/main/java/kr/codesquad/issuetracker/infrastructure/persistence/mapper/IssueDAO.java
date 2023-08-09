package kr.codesquad.issuetracker.infrastructure.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.codesquad.issuetracker.domain.IssueSearch;

@Mapper
public interface IssueDAO {
	List<IssueSimpleMapper> findAll(IssueSearch issueSearch);
}
