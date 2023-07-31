import alertIcon from "@assets/icon/alertCircle.svg";
import archiveIcon from "@assets/icon/archive.svg";
import DropdownIndicator from "@components/Dropdown/DropdownIndicator";
import InputCheckbox from "@components/common/Input/InputCheckbox";
import TabBar from "@components/common/TabBar";
import { styled } from "styled-components";
import TableHeader from "./TableHeader";

export default function TableHeaderIssues() {
  const tabBarLeftInfo = { name: "열린 이슈", count: 2, iconSrc: alertIcon };
  const tabBarRightInfo = {
    name: "닫힌 이슈",
    count: 0,
    iconSrc: archiveIcon,
  };

  return (
    <TableHeader>
      <TableHeaderContents>
        <div className="left-wrapper">
          <InputCheckbox />
          <TabBar
            left={tabBarLeftInfo}
            right={tabBarRightInfo}
            borderStyle="none"
          />
        </div>

        <div className="right-wrapper">
          <DropdownIndicator
            displayName="담당자"
            dropdownPanelVariant="filter"
            dropdownName="assignee"
            dropdownList={[
              {
                variant: "withImg",
                name: "assignee",
                content: "Kakamotobi",
                imgSrc: "https://avatars.githubusercontent.com/u/79886384?v=4",
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
  }

  .right-wrapper {
    display: flex;
    align-items: center;
    gap: 32px;
  }
`;
