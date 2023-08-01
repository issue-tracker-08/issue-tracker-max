import labelIcon from "@assets/icon/label.svg";
import milestoneIcon from "@assets/icon/milestone.svg";
import { useState } from "react";
import styled from "styled-components";
import Button from "./Button";

export default function TabBar({
  labelCount,
  milestoneCount,
}: {
  labelCount: number;
  milestoneCount: number;
}) {
  const [selectedTab, setSelectedTab] = useState<"label" | "milestone" | null>(
    null
  );
  const onLabelButtonClick = () => setSelectedTab("label");
  const onMilestoneButtonClick = () => setSelectedTab("milestone");
  const isLabelSelected = selectedTab === "label";
  const isMilestoneSelected = selectedTab === "milestone";

  return (
    <StyledTabBar>
      <StyledTabButton $selected={isLabelSelected}>
        <Button variant="ghost" size="M" onClick={onLabelButtonClick}>
          <img src={labelIcon} alt="label-icon" />
          <span>레이블({labelCount})</span>
        </Button>
      </StyledTabButton>
      <StyledTabButton $selected={isMilestoneSelected}>
        <Button variant="ghost" size="M" onClick={onMilestoneButtonClick}>
          <img src={milestoneIcon} alt="milestone-icon" />
          <span>마일스톤({milestoneCount})</span>
        </Button>
      </StyledTabButton>
    </StyledTabBar>
  );
}

const StyledTabBar = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 320px;
  height: 40px;

  border: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  border-radius: ${({ theme: { radius } }) => radius.m};

  & > div:first-child {
    border-radius: 11px 0 0 11px;
  }
  & > div:last-child {
    border-radius: 0 11px 11px 0;
    border-left: ${({ theme: { border, neutral } }) =>
      `${border.default} ${neutral.border.default}`};
  }
`;

const StyledTabButton = styled.div<{ $selected: boolean }>`
  width: 100%;
  height: 100%;

  & > button {
    width: 100%;
    height: 100%;
  }

  background-color: ${({ theme: { neutral }, $selected }) =>
    $selected ? neutral.surface.bold : neutral.surface.default};

  & span {
    font: ${({ theme: { font }, $selected }) =>
      $selected ? font.selectedBold16 : font.availableMD16};
    color: ${({ theme: { neutral }, $selected }) =>
      $selected ? neutral.text.strong : neutral.text.default};
  }

  & img {
    filter: ${({ theme: { filter }, $selected }) =>
      $selected ? filter.neutralTextStrong : filter.neutralTextDefault};
  }
`;
