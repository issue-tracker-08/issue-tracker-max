import { ReactElement } from "react";
import { styled } from "styled-components";

export default function TableBody({ children }: { children: ReactElement }) {
  return <StyledTableBody>{children}</StyledTableBody>;
}

const StyledTableBody = styled.div`
  width: 100%;
  background-color: ${({ theme: { neutral } }) => neutral.surface.strong};
  border-bottom-left-radius: ${({ theme: { radius } }) => radius.l};
  border-bottom-right-radius: ${({ theme: { radius } }) => radius.l};
`;
