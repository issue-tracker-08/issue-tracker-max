import { ReactElement } from "react";
import { styled } from "styled-components";

export default function Table({ children }: { children: ReactElement[] }) {
  return <StyledTable>{...children}</StyledTable>;
}

const StyledTable = styled.div`
  width: 100%;
  border: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  border-radius: ${({ theme: { radius } }) => radius.l};
`;
