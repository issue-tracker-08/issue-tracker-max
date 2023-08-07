// import IssueDetailHeader from "@components/Issues/IssueDetail/IssueDetailHeader";
import IssueDetailBody from "@components/Issues/IssueDetail/IssueDetailBody";
import { IssueSidebar } from "@customTypes/index";
import useFetch from "@hooks/useFetch";
import { getIssueSidebar } from "api";
import { useCallback } from "react";
import { useParams } from "react-router-dom";

export default function IssueDetailPage() {
  const { issueId } = useParams();
  const issueNumber = parseInt(issueId!);

  const { data: issueSidebar } = useFetch<IssueSidebar>(
    useCallback(() => getIssueSidebar(issueNumber), [issueNumber])
  );

  // TODO: comments useFetch
  // TODO: fetch comments

  return (
    <>
      {issueSidebar && (
        <IssueDetailBody {...{ issueNumber, ...issueSidebar }} />
      )}
    </>
  );
}
