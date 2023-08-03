import React from "react";
import styled from "styled-components";
import BaseButton from "./BaseButton";
import { SIZE } from "./constants";

export type ButtonVariant = "container" | "outline" | "ghost";
export type ButtonSize = "S" | "M" | "L" | "XL";

export interface ButtonProps
  extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant: ButtonVariant;
  size: ButtonSize;
}

export default function Button({ variant, size, ...props }: ButtonProps) {
  const buttonComponent = {
    container: <ContainerButton $size={size} {...props} />,
    outline: <OutlineButton $size={size} {...props} />,
    ghost: <GhostButton {...props} />,
  };

  return buttonComponent[variant];
}

const ContainerButton = styled(BaseButton)<{
  $size: ButtonSize;
}>`
  width: ${({ $size }) => SIZE[$size].width};
  height: ${({ $size }) => SIZE[$size].height};
  border-radius: ${({ theme: { radius }, $size }) =>
    $size === "L" ? radius.l : radius.m};

  background-color: ${({ theme: { brand } }) => brand.surface.default};
  color: ${({ theme: { brand } }) => brand.text.default};

  & > img {
    filter: ${({ theme: { filter } }) => filter.brandTextDefault};
  }
`;

const OutlineButton = styled(BaseButton)<{
  $size: ButtonSize;
}>`
  width: ${({ $size }) => SIZE[$size].width};
  height: ${({ $size }) => SIZE[$size].height};
  border-radius: ${({ theme: { radius }, $size }) =>
    $size === "L" ? radius.l : radius.m};

  border: ${({ theme: { border, brand } }) =>
    `${border.default} ${brand.border.default}`};
  color: ${({ theme: { brand } }) => brand.text.weak};

  & > img {
    filter: ${({ theme: { filter } }) => filter.brandTextWeak};
  }
`;

const GhostButton = styled(BaseButton)`
  display: flex;
`;
