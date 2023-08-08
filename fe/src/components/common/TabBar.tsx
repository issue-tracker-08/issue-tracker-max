import { useState } from "react";
import styled, { css } from "styled-components";
import Button from "./Button";

type TabBarInfo = {
  iconSrc: string;
  name: string;
  count: number;
  callback?: () => void;
};

export default function TabBar({
  left,
  right,
  borderStyle,
}: {
  left: TabBarInfo;
  right: TabBarInfo;
  borderStyle: "outline" | "none";
}) {
  const [selectedTab, setSelectedTab] = useState<string | null>(null);

  const isRightSelected = selectedTab === right.name;
  const isLeftSelected = selectedTab === left.name;
  const hasOutline = borderStyle === "outline";

  const onLeftClick = () => {
    setSelectedTab(left.name);
    left.callback?.();
  };

  const onRightClick = () => {
    setSelectedTab(right.name);
    right.callback?.();
  };

  return (
    <StyledTabBar $hasOutline={hasOutline}>
      <TabButtonWrapper $selected={isLeftSelected} $hasOutline={hasOutline}>
        <Button variant="ghost" size="M" onClick={onLeftClick}>
          <img
            className="tab-button-icon"
            src={left.iconSrc}
            alt={`${left.name}-icon`}
          />
          <span className="tab-button-text">
            {left.name}({left.count})
          </span>
        </Button>
      </TabButtonWrapper>
      <TabButtonWrapper $selected={isRightSelected} $hasOutline={hasOutline}>
        <Button variant="ghost" size="M" onClick={onRightClick}>
          <img
            className="tab-button-icon"
            src={right.iconSrc}
            alt={`${right.name}-icon`}
          />
          <span className="tab-button-text">
            {right.name}({right.count})
          </span>
        </Button>
      </TabButtonWrapper>
    </StyledTabBar>
  );
}

const StyledTabBar = styled.div<{ $hasOutline: boolean }>`
  display: flex;

  ${({ $hasOutline }) =>
    $hasOutline &&
    css`
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
    `}
`;

const TabButtonWrapper = styled.div<{
  $selected: boolean;
  $hasOutline: boolean;
}>`
  ${({ theme: { neutral }, $selected, $hasOutline }) =>
    $hasOutline &&
    css`
      width: 50%;
      height: 100%;

      & > button {
        width: 100%;
        height: 100%;
      }

      background-color: ${$selected
        ? neutral.surface.bold
        : neutral.surface.default};
    `}

  .tab-button-icon {
    filter: ${({ theme: { filter }, $selected }) =>
      $selected ? filter.neutralTextStrong : filter.neutralTextDefault};
  }

  .tab-button-text {
    font: ${({ theme: { font }, $selected }) =>
      $selected ? font.selectedBold16 : font.availableMD16};
    color: ${({ theme: { neutral }, $selected }) =>
      $selected ? neutral.text.strong : neutral.text.default};
  }
`;
