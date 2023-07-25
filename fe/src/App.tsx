import Logo from "@components/common/Logo";
import GlobalStyle from "@styles/GlobalStyle";
import designSystem from "@styles/designSystem";
import styled, { ThemeProvider } from "styled-components";

export default function App() {
  return (
    <ThemeProvider theme={designSystem}>
      <GlobalStyle />
      <Logo size="large" />
      <H1>적용 되니?</H1>
    </ThemeProvider>
  );
}

const H1 = styled.h1`
  color: ${({ theme: { colors } }) => colors.navy};
  font: ${({ theme: { font } }) => font.displayMD12};
`;
