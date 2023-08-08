import styled from "styled-components";

export const Label = styled.label`
  width: 64px;
  display: flex;
  align-items: center;
  font: ${({ theme: { font } }) => font.displayMD12};
  color: ${({ theme: { neutral } }) => neutral.text.weak};
`;
