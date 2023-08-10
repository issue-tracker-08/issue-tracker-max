import editIcon from "@assets/icon/edit.svg";
import plusIcon from "@assets/icon/plus.svg";
import refreshIcon from "@assets/icon/refreshCcw.svg";
import xIcon from "@assets/icon/xSquare.svg";
import DropdownIndicator from "@components/Dropdown/DropdownIndicator";
import { DropdownItemType } from "@components/Dropdown/types";
import LabelTag from "@components/Label/LabelTag";
import Button from "@components/common/Button";
import RadioGroup from "@components/common/Group/RadioGroup";
import TextInput from "@components/common/TextInput";
import { Label } from "@customTypes/index";
import { generateRandomHexColor, isValidHexColor } from "@utils/style";
import { patchLabel, postLabel } from "api";
import { AxiosError } from "axios";
import { ChangeEvent, FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";

const FontColor = {
  light: "#FEFEFE",
  dark: "#6E7191",
};

const fontColorDropdownList: DropdownItemType[] = [
  {
    id: 0,
    variant: "withColor",
    name: "light",
    content: "light",
    colorFill: FontColor.light,
  },
  {
    id: 1,
    variant: "withColor",
    name: "dark",
    content: "dark",
    colorFill: FontColor.dark,
  },
];

export default function LabelEditor({
  variant,
  label,
  closeEditor,
}: {
  variant: "edit" | "add";
  label?: Label;
  closeEditor: () => void;
}) {
  const navigate = useNavigate();

  const [newLabel, setNewLabel] = useState({
    newName: label ? label.name : "",
    newDescription: label ? label.description : "",
    newBackgroundColor: label ? label.backgroundColor : "#FEFEFE",
    newFontColor: label ? label.fontColor : FontColor.dark,
  });
  const [fontColorDropdownIdx, setFontColorDropdownIdx] = useState(1);

  const { newName, newDescription, newBackgroundColor, newFontColor } =
    newLabel;

  const onNameChange = (e: ChangeEvent<HTMLInputElement>) => {
    setNewLabel((prev) => {
      return { ...prev, newName: e.target.value.trim() };
    });
  };

  const onDescriptionChange = (e: ChangeEvent<HTMLInputElement>) => {
    setNewLabel((prev) => {
      return { ...prev, newDescription: e.target.value.trim() };
    });
  };

  const onBackgroundColorChange = (e: ChangeEvent<HTMLInputElement>) => {
    setNewLabel((prev) => {
      return { ...prev, newBackgroundColor: e.target.value.trim() };
    });
  };

  const onBackgroundColorRandom = () => {
    setNewLabel((prev) => {
      return { ...prev, newBackgroundColor: generateRandomHexColor() };
    });
  };

  const onFontColorSelect = (index: number) => {
    setFontColorDropdownIdx(index);

    setNewLabel((prev) => {
      const selectedDropdownItem = fontColorDropdownList[index] as {
        colorFill: string;
      };

      return { ...prev, newFontColor: selectedDropdownItem.colorFill };
    });
  };

  const isReadyToSubmit = () => {
    return Object.entries(newLabel).every(([key, val]) => {
      if (key === "newDescription") return true;
      return val !== "";
    });
  };

  const onEditFormSubmit = async (e: FormEvent) => {
    e.preventDefault();

    try {
      const newLabel = {
        name: newName,
        description: newDescription,
        backgroundColor: newBackgroundColor,
        fontColor: newFontColor,
      };

      const res =
        variant === "add"
          ? await postLabel(newLabel)
          : await patchLabel(label!.labelId, newLabel);

      if (res.status === 200 || res.status === 201) {
        navigate(0);
        return;
      }

      throw Error((res.data as AxiosError).message);
    } catch (error) {
      // TODO
      console.error(error);
    }
  };

  return (
    <StyledLabelEditor>
      <H2>{variant === "add" ? "새로운 레이블 추가" : "레이블 편집"}</H2>

      <EditForm onSubmit={onEditFormSubmit}>
        <div className="inner-wrapper">
          <Preview>
            <LabelTag
              {...{
                name: newName === "" ? "Label" : newName,
                backgroundColor: newBackgroundColor,
                fontColor: newFontColor,
              }}
            />
          </Preview>

          <InputsWrapper>
            <TextInput
              name="제목"
              variant="short"
              value={newName}
              placeholder="레이블의 이름을 입력하세요"
              onChange={onNameChange}
            />
            <TextInput
              name="설명(선택)"
              variant="short"
              value={newDescription}
              placeholder="레이블에 대한 설명을 입력하세요"
              onChange={onDescriptionChange}
            />
            <div className="colors-container">
              <TextInput
                className="background-color-input"
                name="배경 색상"
                variant="short"
                value={newBackgroundColor}
                placeholder="배경 색상"
                hasError={!isValidHexColor(newBackgroundColor)}
                helpText="Invalid hex color!"
                onChange={onBackgroundColorChange}>
                <Button
                  className="random-color-button"
                  type="button"
                  variant="ghost"
                  size="S"
                  onClick={onBackgroundColorRandom}>
                  <img
                    className="random-color-button-icon"
                    src={refreshIcon}
                    alt="랜덤 배경색 생성"
                  />
                </Button>
              </TextInput>

              <RadioGroup
                value={fontColorDropdownIdx}
                onChange={onFontColorSelect}>
                <DropdownIndicator
                  displayName="텍스트 색상"
                  dropdownPanelVariant="select"
                  dropdownOption="single"
                  dropdownName="fontColor"
                  dropdownList={fontColorDropdownList}
                  dropdownPanelPosition="right"
                />
              </RadioGroup>
            </div>
          </InputsWrapper>
        </div>

        <ButtonsWrapper>
          <Button
            type="button"
            variant="outline"
            size="S"
            onClick={closeEditor}>
            <img src={xIcon} alt="취소" />
            <span>취소</span>
          </Button>
          <Button
            type="submit"
            variant="container"
            size="S"
            disabled={!isReadyToSubmit()}>
            {variant === "add" ? (
              <>
                <img src={plusIcon} alt="완료" />
                <span>완료</span>
              </>
            ) : (
              <>
                <img src={editIcon} alt="편집 완료" />
                <span>편집 완료</span>
              </>
            )}
          </Button>
        </ButtonsWrapper>
      </EditForm>
    </StyledLabelEditor>
  );
}

const StyledLabelEditor = styled.div`
  width: 100%;
`;

const EditForm = styled.form`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 24px;

  .inner-wrapper {
    display: flex;
    gap: 24px;
  }
`;

const H2 = styled.h2`
  margin-bottom: 24px;
  font: ${({ theme: { font } }) => font.displayBold20};
  color: ${({ theme: { neutral } }) => neutral.text.strong};
`;

const Preview = styled.div`
  width: 288px;
  height: 153px;
  display: flex;
  justify-content: center;
  align-items: center;
  border: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  border-radius: ${({ theme: { radius } }) => radius.m};
`;

const InputsWrapper = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 16px;
  flex-grow: 1;

  .colors-container {
    width: 100%;
    display: flex;
    align-items: center;
    gap: 24px;

    .background-color-input {
      width: 208px;

      .random-color-button {
        display: flex;

        .random-color-button-icon {
          display: inline-block;
          filter: ${({ theme: { filter } }) => filter.neutralTextDefault};
        }
      }
    }
  }
`;

const ButtonsWrapper = styled.div`
  display: flex;
  gap: 16px;
  justify-content: flex-end;
`;
