import editIcon from "@assets/icon/edit.svg";
import trashIcon from "@assets/icon/trash.svg";
import LabelEditor from "@components/Label/LabelEditor";
import LabelTag from "@components/Label/LabelTag";
import Button from "@components/common/Button";
import { Label } from "@customTypes/index";
import { deleteLabel } from "api";
import { AxiosError } from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import TableBodyItem from "./TableBodyItem";

export default function TableBodyItemLabel({ label }: { label: Label }) {
  const navigate = useNavigate();

  const [isEditing, setIsEditing] = useState(false);

  const openEditor = () => {
    setIsEditing(true);
  };

  const closeEditor = () => {
    setIsEditing(false);
  };

  const onLabelDelete = async () => {
    try {
      const res = await deleteLabel(label.labelId);

      if (res.status === 204) {
        navigate(0);
        return;
      }

      throw Error((res.data as AxiosError).message);
    } catch (error) {
      // TODO
      console.error(error);
    }
  };

  const { name, backgroundColor, fontColor } = label;

  return (
    <StyledTableBodyItemLabel>
      {isEditing ? (
        <LabelEditor variant="edit" label={label} closeEditor={closeEditor} />
      ) : (
        <>
          <LeftWrapper>
            <LabelTag
              {...{
                name,
                backgroundColor,
                fontColor,
              }}
            />
          </LeftWrapper>

          <CenterWrapper>
            <p className="label-desc">{label.description}</p>
          </CenterWrapper>

          <RightWrapper>
            <Button variant="ghost" size="S" onClick={openEditor}>
              <img className="tab-button-icon" src={editIcon} alt="편집 취소" />
              <span className="tab-button-text">편집</span>
            </Button>
            {/* TODO: onDeleteClick */}
            <Button variant="ghost" size="S" onClick={onLabelDelete}>
              <img
                className="tab-button-icon delete"
                src={trashIcon}
                alt="편집 완료"
              />
              <span className="tab-button-text delete">삭제</span>
            </Button>
          </RightWrapper>
        </>
      )}
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
    font: ${({ theme: { font } }) => font.availableMD12};
    color: ${({ theme: { neutral } }) => neutral.text.default};

    &.delete {
      color: ${({ theme: { danger } }) => danger.text.default};
    }
  }
`;
