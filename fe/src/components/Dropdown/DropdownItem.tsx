import { Avatar } from "@components/common/Avatar";
import CircleCheckbox from "@components/common/Input/CircleCheckbox";
import InputRadio from "@components/common/Input/InputRadio";
import { CheckContext } from "context/checkContext";
import { useContext } from "react";
import { styled } from "styled-components";
import { DropdownItemType } from "./types";

export default function DropdownItem({ item }: { item: DropdownItemType }) {
  const checkContext = useContext(CheckContext);

  const getInput = () => {
    if (checkContext) {
      const circleCheckboxProps: React.InputHTMLAttributes<HTMLInputElement> = {
        onChange: ({ target: { checked } }) =>
          checkContext.toggleCheck({ checked, value: item.id }),
        checked: checkContext.isChecked(item.id),
      };

      return (
        <CircleCheckbox
          name={item.name}
          id={item.content}
          {...circleCheckboxProps}
        />
      );
    }
    return <InputRadio name={item.name} id={item.content} />;
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
            {getInput()}
          </Label>
        );
      case "withColor":
        return (
          <Label htmlFor={item.content}>
            <ColorSwatch $colorFill={item.colorFill} />
            <Content>{item.content}</Content>
            {getInput()}
          </Label>
        );
      case "plain":
        return (
          <Label htmlFor={item.content}>
            <Content>{item.content}</Content>
            {getInput()}
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
