import { useEffect } from "react";
import { styled } from "styled-components";
import DropdownItem from "./DropdownItem";
import { DropdownNameKOR, DropdownPanelType } from "./types";

export default function DropdownPanel({
  dropdownPanel,
}: {
  dropdownPanel: DropdownPanelType;
}) {
  useEffect(() => {
    document.addEventListener("click", dropdownPanel.onOutsideClick);

    return () => {
      document.removeEventListener("click", dropdownPanel.onOutsideClick);
    };
  });

  const generatePanel = (dropdownPanel: DropdownPanelType) => {
    const { variant, dropdownName, dropdownList } = dropdownPanel;

    const canBeNegatory = dropdownName !== "author" && dropdownName !== "issue";
    const suffixKOR =
      dropdownName === "assignee" || dropdownName === "issue" ? "가" : "이";

    switch (variant) {
      case "filter":
        return (
          <>
            <Header>
              <h4>{DropdownNameKOR[dropdownName]} 필터</h4>
            </Header>
            <DropdownList>
              {canBeNegatory && (
                <DropdownItem
                  item={{
                    variant: "plain",
                    name: dropdownName,
                    content: `${DropdownNameKOR[dropdownName]}${suffixKOR} 없는 이슈`,
                  }}
                />
              )}
              {dropdownList.map((item) => {
                return <DropdownItem {...{ key: item.content, item }} />;
              })}
            </DropdownList>
          </>
        );
      case "select":
        return (
          <>
            <Header>
              <h4>{DropdownNameKOR[dropdownName]} 설정</h4>
            </Header>
            <DropdownList>
              {dropdownList.map((item) => {
                return <DropdownItem {...{ key: item.content, item }} />;
              })}
            </DropdownList>
          </>
        );
      case "modify":
        return (
          <>
            <Header>
              <h4>{DropdownNameKOR[dropdownName]} 변경</h4>
            </Header>
            <DropdownList>
              {dropdownList.map((item) => {
                return <DropdownItem {...{ key: item.content, item }} />;
              })}
            </DropdownList>
          </>
        );
      default:
        throw Error("Invalid panel type");
    }
  };

  return (
    <StyledDropdownPanel $position={dropdownPanel.position}>
      {generatePanel(dropdownPanel)}
    </StyledDropdownPanel>
  );
}

const StyledDropdownPanel = styled.div<{ $position: "left" | "right" }>`
  width: 240px;
  display: flex;
  flex-direction: column;
  position: absolute;
  top: 40px;
  left: ${({ $position }) => $position === "left" && "-24px"};
  right: ${({ $position }) => $position === "right" && 0};
  border: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  border-radius: ${({ theme: { radius } }) => radius.l};
  box-shadow: ${({ theme: { boxShadow } }) => boxShadow};
  overflow: hidden;
`;

const Header = styled.header`
  width: 100%;
  padding: 8px 16px;
  background-color: ${({ theme: { neutral } }) => neutral.surface.default};
  border-bottom: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  font: ${({ theme: { font } }) => font.displayMD12};

  h4 {
    color: ${({ theme: { neutral } }) => neutral.text.weak};
  }
`;

const DropdownList = styled.ul`
  width: 100%;
`;
