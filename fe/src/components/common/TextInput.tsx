import { ChangeEvent, ReactNode } from "react";
import styled from "styled-components";
import { Label } from "./Label.style";

interface TextInputProps extends React.HTMLAttributes<HTMLElement> {
  name: string;
  value: string;
  placeholder: string;
  variant: "tall" | "short";
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
  hasError?: boolean;
  helpText?: string;
  children?: ReactNode;
}

export default function TextInput({
  name,
  value,
  placeholder,
  variant,
  onChange,
  hasError,
  helpText,
  children,
  ...props
}: TextInputProps) {
  const isTallType = variant === "tall";

  return (
    <StyledTextInput {...props}>
      <InputContainer $variant={variant} $hasError={hasError}>
        {isTallType && value && <Label htmlFor={name}>{name}</Label>}
        {!isTallType && <Label htmlFor={name}>{name}</Label>}
        <Input
          {...{
            id: name,
            value,
            placeholder,
            onChange,
          }}
        />
        {children}
      </InputContainer>
      {hasError && helpText && (
        <HelpText $hasError={hasError}>{helpText}</HelpText>
      )}
    </StyledTextInput>
  );
}

const INPUT_HEIGHT = {
  tall: 56,
  short: 40,
};

const StyledTextInput = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 4px;
`;

const InputContainer = styled.div<{
  $variant: "tall" | "short";
  $hasError?: boolean;
}>`
  width: 100%;
  height: ${({ $variant }) => INPUT_HEIGHT[$variant]}px;
  padding: 0px 16px;
  display: flex;
  flex-direction: ${({ $variant }) => ($variant === "tall" ? "column" : "row")};
  justify-content: ${({ $variant }) =>
    $variant === "tall" ? "center" : "flex-start"};
  gap: 8px;
  color: ${({ theme: { neutral } }) => neutral.text.weak};
  background-color: ${({ $hasError, theme: { neutral } }) =>
    $hasError ? neutral.surface.strong : neutral.surface.bold};
  border: ${({ $hasError, theme: { border, danger } }) =>
    $hasError && `${border.default} ${danger.text.default}`};
  border-radius: ${({ $variant, theme: { radius } }) =>
    $variant === "tall" ? `${radius.l}` : `${radius.m}`};

  &:focus-within {
    border: ${({ $hasError, theme: { neutral, border, danger } }) =>
      $hasError
        ? `${border.default} ${danger.text.default}`
        : `${border.default} ${neutral.border.defaultActive}`};
    background-color: ${({ theme: { neutral } }) => neutral.surface.strong};
  }

  &:disabled {
    background-color: ${({ theme: { neutral } }) => neutral.surface.bold};
    opacity: ${({ theme: { opacity } }) => opacity.disabled};
  }
`;

const Input = styled.input`
  width: 100%;
  display: flex;
  color: ${({ theme: { neutral } }) => neutral.text.default};
  font: ${({ theme: { font } }) => font.displayMD16};
  caret-color: ${({ theme: { palette } }) => palette.blue};
  &::placeholder {
    color: ${({ theme: { neutral } }) => neutral.text.weak};
  }
  &:focus {
    color: ${({ theme: { neutral } }) => neutral.text.strong};
  }
`;

const HelpText = styled.span<{
  $hasError?: boolean;
}>`
  display: flex;
  color: ${({ $hasError, theme: { neutral, danger } }) =>
    $hasError ? danger.text.default : neutral.text.weak};
  font: ${({ theme: { font } }) => font.displayMD12};
  padding: 0px 16px;
`;
