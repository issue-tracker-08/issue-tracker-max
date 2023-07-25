import { styled } from "styled-components";
import checkOffCircle from "@assets/icon/checkOffCircle.svg";
import checkOnCircle from "@assets/icon/checkOnCircle.svg";
import { DropdownItemType } from "./types";

export default function DropdownItem({ item }: { item: DropdownItemType }) {
  const generateItem = (item: DropdownItemType) => {
    switch (item.variant) {
      case "withImg":
        return (
          <Label htmlFor={item.content}>
            <Avatar
              src={item.imgSrc}
              alt={`${item.variant}: ${item.content}`}
            />
            <Content>{item.content}</Content>
            <input
              className="radio-input"
              type="radio"
              name={item.name}
              id={item.content}
            />
            <img className="radio-img" src={checkOffCircle} alt="" />
          </Label>
        );
      case "withColor":
        return (
          <Label htmlFor={item.content}>
            <ColorSwatch $colorFill={item.colorFill} />
            <Content>{item.content}</Content>
            <input
              className="radio-input"
              type="radio"
              name={item.name}
              id={item.content}
            />
            <img className="radio-img" src={checkOffCircle} alt="" />
          </Label>
        );
      case "plain":
        return (
          <Label htmlFor={item.content}>
            <Content>{item.content}</Content>
            <input
              className="radio-input"
              type="radio"
              name={item.name}
              id={item.content}
            />
            <img className="radio-img" src={checkOffCircle} alt="" />
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
    border-bottom: ${({ theme: { border } }) => border.default};
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

  .radio-input {
    display: none;
  }

  .radio-input:not(:checked) + .radio-img {
    content: url(${checkOffCircle});
  }

  .radio-input:checked + .radio-img {
    content: url(${checkOnCircle});
  }

  .radio-img {
    filter: ${({ theme: { iconFilter } }) => iconFilter};
  }
`;

const Content = styled.div`
  flex-grow: 1;
  font: ${({ theme: { font } }) => font.availableMD16};
  color: ${({ theme: { neutral } }) => neutral.text.default};
  overflow: hidden;
  text-overflow: ellipsis;

  &:has(+ .radio-input:checked) {
    font: ${({ theme: { font } }) => font.selectedBold16};
  }
`;

const Avatar = styled.img`
  widht: 20px;
  height: 20px;
  border-radius: ${({ theme: { radius } }) => radius.half};
  overflow: hidden;
`;

const ColorSwatch = styled.span<{ $colorFill: string }>`
  width: 20px;
  height: 20px;
  background-color: ${({ $colorFill }) => $colorFill};
  border-radius: ${({ theme: { radius } }) => radius.half};
`;
