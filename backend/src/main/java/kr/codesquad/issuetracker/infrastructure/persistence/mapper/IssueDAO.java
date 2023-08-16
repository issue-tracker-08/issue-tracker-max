package kr.codesquad.issuetracker.infrastructure.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.codesquad.issuetracker.domain.IssueSearch;

@Mapper
public interface IssueDAO {

	List<IssueSimpleMapper> findAll(@Param("issueSearch") IssueSearch issueSearch, @Param("offset") int offset,
		@Param("size") int size);

	int countAll(@Param("issueSearch") IssueSearch issueSearch);
}
