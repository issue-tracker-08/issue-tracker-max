import editIcon from "@assets/icon/edit.svg";
import trashIcon from "@assets/icon/trash.svg";
import LabelTag from "@components/LabelTag";
import Button from "@components/common/Button";
import { Label } from "@customTypes/index";
import { styled } from "styled-components";
import TableBodyItem from "./TableBodyItem";

export default function TableBodyItemLabel({ label }: { label: Label }) {
  return (
    <StyledTableBodyItemLabel>
      <LeftWrapper>
        <LabelTag label={label} />
      </LeftWrapper>

      <CenterWrapper>
        <p className="label-desc">label description</p>
      </CenterWrapper>

      <RightWrapper>
        <Button variant="ghost" size="S" onClick={() => ""}>
          <img className="tab-button-icon" src={editIcon} alt="편집 취소" />
          <span className="tab-button-text">편집</span>
        </Button>
        <Button variant="ghost" size="S">
          <img
            className="tab-button-icon delete"
            src={trashIcon}
            alt="편집 완료"
          />
          <span className="tab-button-text delete">삭제</span>
        </Button>
      </RightWrapper>
    </StyledTableBodyItemLabel>
  );
}

const StyledTableBodyItemLabel = styled(TableBodyItem)`
  padding: 32px;
  gap: 32px;
`;

const LeftWrapper = styled.div`
  width: 176px;
`;

const CenterWrapper = styled.div`
  flex-grow: 1;

  .label-desc {
    font: ${({ theme: { font } }) => font.displayMD16};
    color: ${({ theme: { neutral } }) => neutral.text.weak};
  }
`;

const RightWrapper = styled.div`
  display: flex;
  gap: 24px;

  .tab-button-icon {
    filter: ${({ theme: { filter } }) => filter.neutralTextDefault};

    &.delete {
      filter: ${({ theme: { filter } }) => filter.dangerTextDefault};
    }
  }

  .tab-button-text {
    font: ${({ theme: { font } }) => font.availableMD16};
    color: ${({ theme: { neutral } }) => neutral.text.default};

    &.delete {
      color: ${({ theme: { danger } }) => danger.text.default};
    }
  }
`;
