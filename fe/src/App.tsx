import { useState } from "react";
import styled, { ThemeProvider } from "styled-components";
import GlobalStyle from "@styles/GlobalStyle";
import { lightMode, darkMode } from "@styles/designSystem";
import DropdownIndicator from "@components/Dropdown/DropdownIndicator";
import Logo from "@components/common/Logo";

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

      <DropdownIndicator
        dropdownName="assignee"
        dropdownList={[
          {
            type: "withImg",
            name: "assignee",
            content: "Kakamotobiscuitcookie",
            imgSrc: "https://avatars.githubusercontent.com/u/79886384?v=4",
          },
          {
            type: "withImg",
            name: "assignee",
            content: "Zoey",
            imgSrc: "https://avatars.githubusercontent.com/u/111998760?v=4",
          },
          {
            type: "withColor",
            name: "label",
            content: "documentation",
            colorFill: "blue",
          },
          { type: "onlyContent", name: "milestone", content: "FE Sprint #1" },
        ]}
      />
    </ThemeProvider>
  );
}

const H1 = styled.h1`
  color: ${({ theme: { neutral } }) => neutral.text.strong};
  font: ${({ theme: { font } }) => font.displayMD12};
`;
