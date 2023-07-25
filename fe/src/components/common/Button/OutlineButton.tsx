import styled from "styled-components";

import { BaseStyledButton } from "./BaseButton";
import { ButtonProps } from "./Button";
import { HEIGHT, WIDTH } from "./constants";

export default function OutlineButton(props: ButtonProps) {
  return <StyledOutlineButton {...props}></StyledOutlineButton>;
}

export const StyledOutlineButton = styled(BaseStyledButton)<ButtonProps>`
  width: ${({ size }) => size && WIDTH[size]};
  height: ${({ size }) => size && HEIGHT[size]};
  border-radius: ${({ theme, size }) =>
    size === "L" ? theme.radius.l : theme.radius.m};

  border: ${({ theme }) =>
    `${theme.border.default} ${theme.brand.border.default}`};
  color: ${({ theme }) => theme.brand.text.weak};

  & > img {
    filter: ${({ theme }) => theme.filter.brandTextWeak};
  }
`;