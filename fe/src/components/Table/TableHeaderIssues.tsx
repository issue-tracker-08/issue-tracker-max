import alertIcon from "@assets/icon/alertCircle.svg";
import archiveIcon from "@assets/icon/archive.svg";
import DropdownIndicator from "@components/Dropdown/DropdownIndicator";
import GhostButton from "@components/common/Button/GhostButton";
import InputCheckbox from "@components/common/Input/InputCheckbox";
import { styled } from "styled-components";
import TableHeader from "./TableHeader";

export default function TableHeaderIssues() {
  return (
    <TableHeader>
      <TableHeaderContents>
        <div className="left-wrapper">
          <InputCheckbox />

          {/* TODO: TabBar로 변경 */}
          <GhostButton size="M" variant="ghost">
            <img src={alertIcon} alt="열린 이슈" />
            열린 이슈 (2)
          </GhostButton>
          <GhostButton size="M" variant="ghost">
            <img src={archiveIcon} alt="닫힌 이슈" />
            닫힌 이슈 (0)
          </GhostButton>
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

    button:first-of-type {
      margin-right: 24px;
    }

    button {
      color: ${({ theme: { neutral } }) => neutral.text.default};
      font: ${({ theme: { font } }) => font.availableMD16};

      img {
        filter: ${({ theme: { filter } }) => filter.neutralTextDefault};
      }
    }
  }

  .right-wrapper {
    display: flex;
    align-items: center;
    gap: 32px;
  }
`;
