import { ReactElement } from "react";
import { styled } from "styled-components";

export default function TableHeader({ children }: { children: ReactElement }) {
  return <StyledTableHeader>{children}</StyledTableHeader>;
}

const StyledTableHeader = styled.div`
  width: 100%;
  height: 64px;
  padding: 16px 32px;
  display: flex;
  align-items: center;
  background-color: ${({ theme: { neutral } }) => neutral.surface.default};
  border-bottom: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  border-top-left-radius: ${({ theme: { radius } }) => radius.l};
  border-top-right-radius: ${({ theme: { radius } }) => radius.l};
`;
