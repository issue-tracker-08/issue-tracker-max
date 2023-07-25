import { useState } from "react";
import { styled } from "styled-components";
import DropdownPanel from "@components/Dropdown/DropdownPanel";
import chevronDown from "@assets/icon/chevronDown.svg";
import { DropdownNameKOR, DropdownName, DropdownItemType } from "./types";

export default function DropdownIndicator({
  dropdownName,
  dropdownList,
}: {
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
        <span>{DropdownNameKOR[dropdownName]}</span>
        <img src={chevronDown} alt={`Filter by ${dropdownName}`} />
      </Button>

      {isOpen && (
        <DropdownPanel
          dropdownPanel={{
            panelType: "filter",
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
  margin-left: 300px; // Remove this!
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

  span {
    color: ${({ theme: { neutral } }) => neutral.text.default};
    font: ${({ theme: { font } }) => font.availableMD16};
  }

  img {
    width: 16px;
    height: 16px;
    transform: ${({ $isOpen }) => ($isOpen ? "rotate(180deg)" : "")};
  }
`;
