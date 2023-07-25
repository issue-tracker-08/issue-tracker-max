import { styled } from "styled-components";
import DropdownItem from "./DropdownItem";
import { DropdownNameKOR, DropdownName, DropdownItemType } from "./types";

export default function DropdownPanel({
  dropdownName,
  dropdownList,
}: {
  dropdownName: DropdownName;
  dropdownList: DropdownItemType[];
}) {
  return (
    <StyledDropdownPanel>
      <Header>
        <h4>{DropdownNameKOR[dropdownName]} 필터</h4>
      </Header>
      <DropdownList>
        {dropdownName !== "author" && (
          <DropdownItem
            item={{
              type: "onlyContent",
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
    </StyledDropdownPanel>
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
