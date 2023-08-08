import Sidebar from "@components/common/Sidebar/Sidebar";
import { compareSet } from "@utils/compareSet";
import { postEditField } from "api";
import _ from "lodash";
import { useRef, useState } from "react";
import styled from "styled-components";

type IssueDetailBodyProps = {
  issueNumber: number;
  assignees: number[];
  labels: number[];
  milestone: number;
};

export default function IssueDetailBody({
  issueNumber,
  assignees,
  labels,
  milestone,
}: IssueDetailBodyProps) {
  const [issueDetail, setIssueDetail] = useState<{
    assignees: Set<number>;
    labels: Set<number>;
    milestone: number;
  }>({
    assignees: new Set<number>(assignees),
    labels: new Set<number>(labels),
    milestone: milestone,
  });

  const prevIssueDetail = useRef(_.cloneDeep(issueDetail));

  const onAssigneeChange = (assignees: Set<number>) => {
    setIssueDetail((prev) => ({ ...prev, assignees }));
  };

  const onLabelChange = (labels: Set<number>) => {
    setIssueDetail((prev) => ({ ...prev, labels }));
  };

  const onMilestoneChange = (milestone: number) => {
    setIssueDetail((prev) => ({ ...prev, milestone }));
  };

  const onEditIssue = () => {
    const editedInfo = compareSet(
      prevIssueDetail.current.assignees,
      issueDetail.assignees
    );
    postEditField(issueNumber, "assignees", editedInfo);
  };

  const onEditLabels = () => {
    const editedInfo = compareSet(
      prevIssueDetail.current.labels,
      issueDetail.labels
    );
    postEditField(issueNumber, "labels", editedInfo);
  };

  // TODO: 마일스톤 없는 이슈 DropdownItem 추가
  const onEditMilestone = () => {
    const isNotModified =
      prevIssueDetail.current.milestone === issueDetail.milestone;
    const isRemoved = issueDetail.milestone === 0;

    const editedInfo = {
      milestoneId: isRemoved ? null : issueDetail.milestone,
    };
    !isNotModified && postEditField(issueNumber, "milestone", editedInfo);
  };

  return (
    <Body>
      <div className="comments-container">
        {/* TODO: 이슈 Content Comment */}
        {/* TODO: comments.map() */}
        {/* TODO: 새 코멘트 작성 text area */}
      </div>
      <Sidebar
        {...{
          assignees: issueDetail.assignees,
          labels: issueDetail.labels,
          milestone: issueDetail.milestone,
          onAssigneeChange,
          onLabelChange,
          onMilestoneChange,
          onEditIssue,
          onEditLabels,
          onEditMilestone,
        }}
      />
    </Body>
  );
}

const Body = styled.div`
  width: 100%;
  padding-top: 24px;
  display: flex;
  gap: 32px;
  border-top: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};

  .comments-container {
    display: flex;
    flex-direction: column;
    gap: 24px;
    flex-grow: 1;
  }
`;
