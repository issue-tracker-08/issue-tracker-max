import { styled } from "styled-components";

export default styled.div`
  width: 100%;
  height: 96px;
  color: ${({ theme: { neutral } }) => neutral.text.weak};
  font: ${({ theme: { font } }) => font.displayMD16};
  line-height: 96px;
  text-align: center;
`;
