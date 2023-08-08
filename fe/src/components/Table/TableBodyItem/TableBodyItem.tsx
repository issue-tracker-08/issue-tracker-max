import { styled } from "styled-components";

export default styled.li`
  width: 100%;
  height: inherit;
  padding: 16px 32px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  &:not(:last-child) {
    border-bottom: ${({ theme: { border, neutral } }) =>
      `${border.default} ${neutral.border.default}`};
  }
`;
