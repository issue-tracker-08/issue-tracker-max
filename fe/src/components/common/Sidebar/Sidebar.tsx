import styled from "styled-components";
import AssigneeField from "./AssigneeField";
import LabelField from "./LabelField";
import MilestoneField from "./MilestoneField";

export default function Sidebar({
  assignees,
  labels,
  milestone,
  onAssigneeChange,
  onLabelChange,
  onMilestoneChange,
}: {
  assignees: Set<number>;
  labels: Set<number>;
  milestone: number;
  onAssigneeChange: (assignees: Set<number>) => void;
  onLabelChange: (labels: Set<number>) => void;
  onMilestoneChange: (milestone: number) => void;
}) {
  return (
    <StyledSidebar>
      <AssigneeField {...{ assignees, onAssigneeChange }} />
      <LabelField {...{ labels, onLabelChange }} />
      <MilestoneField {...{ milestone, onMilestoneChange }} />
    </StyledSidebar>
  );
}

const StyledSidebar = styled.div`
  width: 288px;
  border: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  border-radius: ${({ theme: { radius } }) => radius.l};
  background-color: ${({ theme: { neutral } }) => neutral.surface.strong};

  > div:not(:last-child) {
    border-bottom: ${({ theme: { border, neutral } }) =>
      `${border.default} ${neutral.border.default}`};
  }

  #dropdown-indicator-assignee,
  #dropdown-indicator-label,
  #dropdown-indicator-milestone {
    width: 100%;
  }
`;
