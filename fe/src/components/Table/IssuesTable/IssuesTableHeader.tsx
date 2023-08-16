import alertIcon from "@assets/icon/alertCircle.svg";
import archiveIcon from "@assets/icon/archive.svg";
import DropdownIndicator from "@components/Dropdown/DropdownIndicator";
import { DropdownItemType } from "@components/Dropdown/types";
import RadioGroup from "@components/common/Group/RadioGroup";
import InputCheckbox from "@components/common/Input/InputCheckbox";
import TabBar from "@components/common/TabBar";
import { putIssuesIsOpen } from "api";
import { useState } from "react";
import { styled } from "styled-components";
import { TableHeader } from "../Table.style";

const issueStateDropdownList: DropdownItemType[] = [
  {
    id: 0,
    variant: "plain",
    name: "issueState",
    content: "선택한 이슈 열기",
  },
  {
    id: 1,
    variant: "plain",
    name: "issueState",
    content: "선택한 이슈 닫기",
  },
];

export default function IssuesTableHeader({
  numOpen,
  numClosed,
  isAllIssuesSelected,
  selectedIssueIds,
  toggleSelectAll,
  refetchIssuesList,
}: {
  numOpen: number;
  numClosed: number;
  isAllIssuesSelected: boolean;
  selectedIssueIds: Set<number>;
  toggleSelectAll: () => void;
  refetchIssuesList: () => void;
}) {
  const [issueStateDropdownIdx, setIssueStateDropdownIdx] = useState(0);

  const onIssueStateSelect = (index: number) => {
    setIssueStateDropdownIdx(index);
  };

  const onUpdateSelectedIssuesState = async () => {
    try {
      const res = await putIssuesIsOpen({
        issueIds: selectedIssueIds ? [...selectedIssueIds] : [],
        state: issueStateDropdownIdx === 0 ? "open" : "closed",
      });

      if (res.status === 200) {
        refetchIssuesList();
        setIssueStateDropdownIdx(0);
      }
    } catch (error) {
      // TODO
      console.error(error);
    }
  };

  const tabBarLeftInfo = {
    name: "열린 이슈",
    count: numOpen,
    iconSrc: alertIcon,
    callback: () => console.log("열린 이슈"),
  };
  const tabBarRightInfo = {
    name: "닫힌 이슈",
    count: numClosed,
    iconSrc: archiveIcon,
    callback: () => console.log("닫힌 이슈"),
  };

  const numSelectedIssues = selectedIssueIds.size;

  return (
    <TableHeader>
      <TableHeaderContents>
        <div className="left-wrapper">
          <InputCheckbox
            checked={isAllIssuesSelected}
            onChange={toggleSelectAll}
          />
          {numSelectedIssues > 0 ? (
            <div className="num-selected-issues">
              {numSelectedIssues}개 이슈 선택
            </div>
          ) : (
            <TabBar
              currentTabName="열린 이슈"
              left={tabBarLeftInfo}
              right={tabBarRightInfo}
              borderStyle="none"
            />
          )}
        </div>

        <div className="right-wrapper">
          {numSelectedIssues > 0 ? (
            <RadioGroup
              value={issueStateDropdownIdx}
              onChange={onIssueStateSelect}>
              <DropdownIndicator
                displayName="상태수정"
                dropdownPanelVariant="select"
                dropdownName="issueState"
                dropdownList={issueStateDropdownList}
                dropdownPanelPosition="right"
                outsideClickHandler={onUpdateSelectedIssuesState}
              />
            </RadioGroup>
          ) : (
            <>
              <DropdownIndicator
                displayName="담당자"
                dropdownPanelVariant="filter"
                dropdownName="assignee"
                dropdownList={[
                  {
                    id: 1,
                    variant: "withImg",
                    name: "assignee",
                    content: "Kakamotobi",
                    imgSrc:
                      "https://avatars.githubusercontent.com/u/79886384?v=4",
                  },
                ]}
                dropdownPanelPosition="right"
              />
              <DropdownIndicator
                displayName="레이블"
                dropdownPanelVariant="filter"
                dropdownName="label"
                dropdownList={[]}
                dropdownPanelPosition="right"
              />
              <DropdownIndicator
                displayName="마일스톤"
                dropdownPanelVariant="filter"
                dropdownName="milestone"
                dropdownList={[]}
                dropdownPanelPosition="right"
              />
              <DropdownIndicator
                displayName="작성자"
                dropdownPanelVariant="filter"
                dropdownName="author"
                dropdownList={[]}
                dropdownPanelPosition="right"
              />
            </>
          )}
        </div>
      </TableHeaderContents>
    </TableHeader>
  );
}

const TableHeaderContents = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;

  .left-wrapper {
    display: flex;
    align-items: center;

    > *:first-child {
      margin-right: 32px;
    }

    > *:last-child {
      gap: 24px;
    }

    .num-selected-issues {
      font: ${({ theme: { font } }) => font.displayBold16};
      color: ${({ theme: { neutral } }) => neutral.text.default};
    }
  }

  .right-wrapper {
    display: flex;
    align-items: center;
    gap: 32px;
  }
`;
