import { styled } from "styled-components";
import DropdownItem from "./DropdownItem";
import { DropdownNameKOR, DropdownPanelType } from "./types";

export default function DropdownPanel({
  dropdownPanel,
}: {
  dropdownPanel: DropdownPanelType;
}) {
  const generatePanel = (dropdownPanel: DropdownPanelType) => {
    const { variant, dropdownName, dropdownList } = dropdownPanel;

    switch (variant) {
      case "filter":
        return (
          <>
            <Header>
              <h4>{DropdownNameKOR[dropdownName]} 필터</h4>
            </Header>
            <DropdownList>
              {dropdownName !== "author" && (
                <DropdownItem
                  item={{
                    variant: "plain",
                    name: dropdownName,
                    content: `${DropdownNameKOR[dropdownName]}${
                      dropdownName === "assignee" ? "가" : "이"
                    } 없는 이슈`,
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
                return <DropdownItem {...{ key: item.content, item }} />; // TODO: change the `key` value!
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
    <StyledDropdownPanel>{generatePanel(dropdownPanel)}</StyledDropdownPanel>
  );
}

const StyledDropdownPanel = styled.div`
  width: 240px;
  display: flex;
  flex-direction: column;
  position: absolute;
  right: 0;
  border: ${({ theme: { border } }) => border.default};
  border-radius: ${({ theme: { radius } }) => radius.l};
  overflow: hidden;
  box-shadow: ${({ theme: { boxShadow } }) => boxShadow};
`;

const Header = styled.header`
  width: 100%;
  padding: 8px 16px;
  background-color: ${({ theme: { neutral } }) => neutral.surface.default};
  border-bottom: ${({ theme: { border } }) => border.default};
  font: ${({ theme: { font } }) => font.displayMD12};

  h4 {
    color: ${({ theme: { neutral } }) => neutral.text.weak};
  }
`;

const DropdownList = styled.ul`
  width: 100%;
`;
