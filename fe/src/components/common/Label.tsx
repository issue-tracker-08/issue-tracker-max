import styled from "styled-components";

export const Label = styled.label`
  display: flex;
  align-items: center;
  font: ${({ theme: { font } }) => font.displayMD12};
  color: ${({ theme: { neutral } }) => neutral.text.weak};
`;
