import alertIcon from "@assets/icon/alertCircle.svg";
import archiveIcon from "@assets/icon/archive.svg";
import DropdownIndicator from "@components/Dropdown/DropdownIndicator";
import InputCheckbox from "@components/common/Input/InputCheckbox";
import TabBar from "@components/common/TabBar";
import { styled } from "styled-components";
import { TableHeader } from "../Table.style";

export default function IssuesTableHeader({
  numOpen,
  numClosed,
  numSelectedIssues,
  isAllIssuesSelected,
  toggleSelectAll,
}: {
  numOpen: number;
  numClosed: number;
  numSelectedIssues: number;
  isAllIssuesSelected: boolean;
  toggleSelectAll: () => void;
}) {
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
          {/* TODO: dropdownList */}
          {numSelectedIssues > 0 ? (
            <div>dropdown</div>
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
