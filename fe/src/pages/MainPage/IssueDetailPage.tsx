import IssueDetailBody from "@components/Issues/IssueDetail/IssueDetailBody";
import IssueDetailHeader from "@components/Issues/IssueDetail/IssueDetailHeader";
import { IssueDetail } from "@customTypes/index";
import useFetch from "@hooks/useFetch";
import { getIssue } from "api";
import { useCallback } from "react";
import { useParams } from "react-router-dom";

export default function IssueDetailPage() {
  const { issueId } = useParams();
  const issueNumber = parseInt(issueId!);

  const [issueObj, updateIssueObj] = useFetch<IssueDetail>(
    {
      issueId: 0,
      title: "",
      isOpen: false,
      createdAt: new Date().toLocaleString(),
      author: {
        username: "",
        profileUrl: "",
      },
      content: "",
      assignees: [],
      labels: [],
      milestone: {
        milestoneId: 0,
        milestoneName: "",
        openIssueCount: 0,
        closedIssueCount: 0,
      },
    },
    useCallback(() => getIssue(issueNumber), [issueNumber])
  );

  const isFetchedComplete = !!issueObj.issueId;

  // TODO: fetch comments
  const comments: unknown[] = [];

  const { assignees, labels, milestone } = issueObj;

  // TODO: 이슈 상세 조회 API ID만 받도록 개선 필요 (받은대로 그대로 props로 전달)
  const assigneeIds = assignees.map((assignee) => assignee.userAccountId);
  const labelIds = labels.map((label) => label.labelId);
  const milestoneId = milestone.milestoneId;

  return (
    <>
      {isFetchedComplete && (
        <IssueDetailHeader
          issueId={parseInt(issueId!)}
          numComments={comments.length}
        />
      )}
      {isFetchedComplete && (
        <IssueDetailBody
          {...{
            issueNumber,
            assignees: assignees && assigneeIds,
            labels: labels && labelIds,
            milestone: milestone && milestoneId,
          }}
        />
      )}
    </>
  );
}
