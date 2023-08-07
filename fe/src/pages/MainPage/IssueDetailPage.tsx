import IssueDetailBody from "@components/Issues/IssueDetail/IssueDetailBody";
import IssueDetailHeader from "@components/Issues/IssueDetail/IssueDetailHeader";
import { useParams } from "react-router-dom";

export default function IssueDetailPage() {
  const { issueId } = useParams();
  const issueNumber = parseInt(issueId!);

  return (
    <>
      <IssueDetailHeader issueNumber={issueNumber} />
      <IssueDetailBody issueNumber={issueNumber} />
    </>
  );
}
