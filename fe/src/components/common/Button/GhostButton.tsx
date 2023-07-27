import styled from "styled-components";
import BaseButton from "./BaseButton";
import { ButtonProps, ButtonVariant } from "./Button";

export default function GhostButton({ variant, ...props }: ButtonProps) {
  return <StyledGhostButton $variant={variant} {...props} />;
}

const StyledGhostButton = styled(BaseButton)<{
  $variant: ButtonVariant;
}>`
  // padding: 0 24px;
`;
