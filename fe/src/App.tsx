import DropdownIndicator from "@components/Dropdown/DropdownIndicator";
import FilterBar from "@components/FilterBar";
import Logo from "@components/common/Logo";
import GlobalStyle from "@styles/GlobalStyle";
import { darkMode, lightMode } from "@styles/designSystem";
import { useState } from "react";
import styled, { ThemeProvider } from "styled-components";

export default function App() {
  const [themeMode, setThemeMode] = useState<"light" | "dark">("light");

  const toggleThemeMode = () => {
    setThemeMode((prev) => {
      return prev === "light" ? "dark" : "light";
    });
  };

  return (
    <ThemeProvider theme={themeMode === "light" ? lightMode : darkMode}>
      <GlobalStyle />
      <Logo size="large" />
      <H1>적용 되니?</H1>
      <button type="button" onClick={toggleThemeMode}>
        Theme Mode
      </button>

      <div style={{ marginLeft: "200px", marginBottom: "250px" }}>
        <FilterBar />
      </div>

      <div style={{ marginLeft: "200px", display: "flex", gap: "200px" }}>
        <DropdownIndicator
          displayName="담당자"
          dropdownPanelVariant="filter"
          dropdownName="assignee"
          dropdownList={[
            {
              variant: "withImg",
              name: "assignee",
              content: "Kakamotobiscuitcookie",
              imgSrc: "https://avatars.githubusercontent.com/u/79886384?v=4",
            },
            {
              variant: "withImg",
              name: "assignee",
              content: "Zoey",
              imgSrc: "https://avatars.githubusercontent.com/u/111998760?v=4",
            },
          ]}
          dropdownPanelPosition="right"
        />

        <DropdownIndicator
          displayName="레이블"
          dropdownPanelVariant="filter"
          dropdownName="label"
          dropdownList={[
            {
              variant: "withColor",
              name: "label",
              content: "documentation",
              colorFill: "blue",
            },
            {
              variant: "withColor",
              name: "label",
              content: "bug",
              colorFill: "red",
            },
          ]}
          dropdownPanelPosition="right"
        />

        <DropdownIndicator
          displayName="마일스톤"
          dropdownPanelVariant="filter"
          dropdownName="milestone"
          dropdownList={[
            {
              variant: "plain",
              name: "milestone",
              content: "FE Sprint#1",
            },
            {
              variant: "plain",
              name: "milestone",
              content: "BE Sprint#1",
            },
          ]}
          dropdownPanelPosition="right"
        />

        <DropdownIndicator
          displayName="작성자"
          dropdownPanelVariant="filter"
          dropdownName="author"
          dropdownList={[
            {
              variant: "withImg",
              name: "assignee",
              content: "Kakamotobiscuitcookie",
              imgSrc: "https://avatars.githubusercontent.com/u/79886384?v=4",
            },
            {
              variant: "withImg",
              name: "assignee",
              content: "Zoey",
              imgSrc: "https://avatars.githubusercontent.com/u/111998760?v=4",
            },
          ]}
          dropdownPanelPosition="right"
        />

        <DropdownIndicator
          displayName="상태"
          dropdownPanelVariant="modify"
          dropdownName="issueState"
          dropdownList={[
            {
              variant: "plain",
              name: "issueState",
              content: "선택한 이슈 열기",
            },
            {
              variant: "plain",
              name: "issueState",
              content: "선택한 이슈 닫기",
            },
          ]}
          dropdownPanelPosition="right"
        />
      </div>
    </ThemeProvider>
  );
}

const H1 = styled.h1`
  color: ${({ theme: { neutral } }) => neutral.text.strong};
  font: ${({ theme: { font } }) => font.displayMD12};
`;
