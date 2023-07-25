import { useState } from "react";
import Logo from "@components/common/Logo";
import GlobalStyle from "@styles/GlobalStyle";
import { lightMode, darkMode } from "@styles/designSystem";
import styled, { ThemeProvider } from "styled-components";

export default function App() {
  const [themeMode, setThemeMode] = useState<"light" | "dark">("dark");

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
      <button onClick={toggleThemeMode}>Theme Mode</button>
    </ThemeProvider>
  );
}

const H1 = styled.h1`
  color: ${({ theme: { neutral } }) => neutral.text.strong};
  font: ${({ theme: { font } }) => font.displayMD12};
`;
