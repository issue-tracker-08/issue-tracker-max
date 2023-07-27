import searchIcon from "@assets/icon/search.svg";
import { styled } from "styled-components";
import DropdownIndicator from "./Dropdown/DropdownIndicator";
import { DropdownItemType } from "./Dropdown/types";

export default function FilterBar() {
  const filterOptions: DropdownItemType[] = [
    { variant: "plain", name: "issue", content: "열린 이슈" },
    { variant: "plain", name: "issue", content: "내가 작성한 이슈" },
    { variant: "plain", name: "issue", content: "나에게 할당된 이슈" },
    {
      variant: "plain",
      name: "issue",
      content: "내가 댓글을 남긴 이슈",
    },
    { variant: "plain", name: "issue", content: "닫힌 이슈" },
  ];

  return (
    <StyledFilterBar>
      <FilterButtonContainer className="filter-button-container">
        <DropdownIndicator
          displayName="필터"
          dropdownPanelVariant="filter"
          dropdownName="issue"
          dropdownList={filterOptions}
          dropdownPanelPosition="left"
        />
      </FilterButtonContainer>

      <FilterForm className="filter-form">
        <img src={searchIcon} alt="Search Filter" />
        <FilterInput type="text" placeholder="Search all issues" />
      </FilterForm>
    </StyledFilterBar>
  );
}

const StyledFilterBar = styled.div`
  width: 560px;
  height: 40px;
  display: flex;
  border: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  border-radius: ${({ theme: { radius } }) => radius.m};

  &:focus-within {
    border: ${({ theme: { border, neutral } }) =>
      `${border.default} ${neutral.border.defaultActive}`};

    .filter-button-container {
      background-color: ${({ theme: { neutral } }) => neutral.surface.strong};
    }

    .filter-form {
      background-color: ${({ theme: { neutral } }) => neutral.surface.strong};
    }
  }
`;

const FilterButtonContainer = styled.div`
  height: 100%;
  padding-inline: 24px;
  display: flex;
  align-items: center;
  background-color: ${({ theme: { neutral } }) => neutral.surface.default};
  border-right: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  border-top-left-radius: ${({ theme: { radius } }) => radius.m};
  border-bottom-left-radius: ${({ theme: { radius } }) => radius.m};
`;

const FilterForm = styled.form`
  padding-inline: 24px;
  display: flex;
  align-items: center;
  flex-grow: 1;
  gap: 8px;
  background-color: ${({ theme: { neutral } }) => neutral.surface.bold};
  border-top-right-radius: ${({ theme: { radius } }) => radius.m};
  border-bottom-right-radius: ${({ theme: { radius } }) => radius.m};

  img {
    width: 16px;
    height: 16px;
    filter: ${({ theme: { filter } }) => filter.neutralTextDefault};
  }
`;

const FilterInput = styled.input`
  width: 100%;
  height: 100%;
  background: none;
  border: none;
  color: ${({ theme: { neutral } }) => neutral.text.weak};
  font: ${({ theme: { font } }) => font.displayMD16};
  outline: none;
`;
