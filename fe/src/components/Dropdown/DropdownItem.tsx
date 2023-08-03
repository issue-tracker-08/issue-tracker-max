import { Avatar } from "@components/common/Avatar";
import CircleCheckbox from "@components/common/Input/CircleCheckbox";
import InputRadio from "@components/common/Input/InputRadio";
import { CheckboxContext } from "context/checkboxContext";
import { RadioContext } from "context/radioContext";
import { useContext } from "react";
import { styled } from "styled-components";
import { DropdownItemType, DropdownOption } from "./types";

export default function DropdownItem({
  option,
  item,
}: {
  option: DropdownOption;
  item: DropdownItemType;
}) {
  const checkContext = useContext(CheckboxContext);
  const radioContext = useContext(RadioContext);

  const renderInput = () => {
    const inputMap = {
      multiple: checkContext && (
        <CircleCheckbox
          name={item.name}
          id={item.content}
          onChange={({ target: { checked } }) =>
            checkContext.toggleCheck({ checked, value: item.id })
          }
          checked={checkContext.isChecked(item.id)}
        />
      ),
      single: radioContext && (
        <InputRadio
          name={item.name}
          id={item.content}
          value={item.id}
          checked={radioContext.value === item.id}
          onChange={() => radioContext.onChange(item.id)}
        />
      ),
      default: <InputRadio name={item.name} id={item.content} />,
    };

    return inputMap[option] || inputMap.default;
  };

  const generateItem = (item: DropdownItemType) => {
    switch (item.variant) {
      case "withImg":
        return (
          <Label htmlFor={item.content}>
            <Avatar
              src={item.imgSrc}
              alt={`${item.name}: ${item.content}`}
              $size="S"
            />
            <Content>{item.content}</Content>
            {renderInput()}
          </Label>
        );
      case "withColor":
        return (
          <Label htmlFor={item.content}>
            <ColorSwatch $colorFill={item.colorFill} />
            <Content>{item.content}</Content>
            {renderInput()}
          </Label>
        );
      case "plain":
        return (
          <Label htmlFor={item.content}>
            <Content>{item.content}</Content>
            {renderInput()}
          </Label>
        );
      default:
        throw Error("Invalid dropdown item variant");
    }
  };

  return <StyledDropdownItem>{generateItem(item)}</StyledDropdownItem>;
}

const StyledDropdownItem = styled.li`
  width: 100%;
  background-color: ${({ theme: { neutral } }) => neutral.surface.strong};

  &:hover {
    background-color: ${({ theme: { neutral } }) => neutral.surface.bold};
  }

  &:not(:last-child) {
    border-bottom: ${({ theme: { border, neutral } }) =>
      `${border.default} ${neutral.border.default}`};
  }
`;

const Label = styled.label`
  width: 100%;
  height: 100%;
  padding: 8px 16px;
  display: flex;
  justify-content: space-between;
  gap: 8px;
  cursor: pointer;
`;

const Content = styled.div`
  flex-grow: 1;
  font: ${({ theme: { font } }) => font.availableMD16};
  color: ${({ theme: { neutral } }) => neutral.text.default};
  overflow: hidden;
  text-overflow: ellipsis;

  &:has(+ .input-radio input[type="radio"]:checked) {
    font: ${({ theme: { font } }) => font.selectedBold16};
  }
`;

const ColorSwatch = styled.span<{ $colorFill: string }>`
  width: 20px;
  height: 20px;
  background-color: ${({ $colorFill }) => $colorFill};
  border-radius: ${({ theme: { radius } }) => radius.half};
`;
