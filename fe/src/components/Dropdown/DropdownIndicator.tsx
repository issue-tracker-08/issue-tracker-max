import { useState } from "react";
import { styled } from "styled-components";
import DropdownPanel from "@components/Dropdown/DropdownPanel";
import chevronDown from "@assets/icon/chevronDown.svg";
import {
  DropdownNameKOR,
  DropdownName,
  DropdownPanelVariant,
  DropdownItemType,
} from "./types";

export default function DropdownIndicator({
  dropdownPanelVariant,
  dropdownName,
  dropdownList,
}: {
  dropdownPanelVariant: DropdownPanelVariant;
  dropdownName: DropdownName;
  dropdownList: DropdownItemType[];
}) {
  const [isOpen, setIsOpen] = useState(true);

  const onDropdownClick = () => {
    setIsOpen((prev) => !prev);
  };

  return (
    <StyledDropdownIndicator>
      <Button type="button" $isOpen={isOpen} onClick={onDropdownClick}>
        <span>{`${DropdownNameKOR[dropdownName]} ${
          dropdownPanelVariant === "modify" ? "수정" : ""
        }`}</span>
        <img src={chevronDown} alt={`Filter by ${dropdownName}`} />
      </Button>

      {isOpen && (
        <DropdownPanel
          dropdownPanel={{
            variant: dropdownPanelVariant,
            dropdownName,
            dropdownList,
          }}
        />
      )}
    </StyledDropdownIndicator>
  );
}

const StyledDropdownIndicator = styled.div`
  width: 80px;
  height: 32px;
  position: relative;
  margin-left: 200px; // Remove this!
`;

const Button = styled.button<{ $isOpen: boolean }>`
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: none;
  border: none;
  cursor: pointer;

  &:hover {
    opacity: ${({ theme: { opacity } }) => opacity.hover};
  }

  &:active {
    opacity: ${({ theme: { opacity } }) => opacity.press};
  }

  &:disabled {
    opacity: ${({ theme: { opacity } }) => opacity.disabled};
  }

  span {
    color: ${({ theme: { neutral } }) => neutral.text.default};
    font: ${({ theme: { font } }) => font.availableMD16};
  }

  img {
    width: 16px;
    height: 16px;
    filter: ${({ theme: { iconFilter } }) => iconFilter};
    transform: ${({ $isOpen }) => ($isOpen ? "rotate(180deg)" : "")};
  }
`;
