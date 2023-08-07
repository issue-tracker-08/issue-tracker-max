import IssueDetailHeader from "@components/Issues/IssueDetail/IssueDetailHeader";
import { IssueDetails } from "@customTypes/index";
import useFetch from "@hooks/useFetch";
import { getIssue } from "api";
import { useCallback } from "react";
import { useParams } from "react-router-dom";

export default function IssueDetailPage() {
  const { issueId } = useParams();
  const issueNumber = parseInt(issueId!);

  const {
    data: issueDetails,
    errorMessage: issueDetailsErrorMessage,
    setData: updateIssueDetails,
  } = useFetch<IssueDetails>(
    useCallback(() => getIssue(issueNumber), [issueNumber])
  );

  // TODO: sidebar useFetch
  // TODO: comments useFetch

  const updateIssueTitle = (newTitle: string) => {
    updateIssueDetails((prev) => {
      return prev ? { ...prev, title: newTitle } : prev;
    });
  };

  // TODO: fetch comments
  const comments = [];

  return (
    <>
      {issueDetails && (
        <IssueDetailHeader
          {...{
            issueDetails,
            issueDetailsErrorMessage,
            updateIssueTitle,
            numComments: comments.length,
          }}
        />
      )}
    </>
  );
}
