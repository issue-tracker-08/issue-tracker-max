import styled from "styled-components";
import { Label } from "./Label";

interface TextInputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  name: string;
  variant: "tall" | "short";
  hasError?: boolean;
  helpText?: string;
}

export default function TextInput({
  name,
  value,
  variant,
  hasError,
  helpText,
  ...props
}: TextInputProps) {
  const isTallType = variant === "tall";

  return (
    <StyledTextInput>
      <InputContainer $variant={variant} $hasError={hasError}>
        {isTallType && value && (
          <Label htmlFor={name}>{props.placeholder}</Label>
        )}
        {!isTallType && <Label htmlFor={name}>{props.placeholder}</Label>}
        <Input id={name} {...props} />
      </InputContainer>
      {hasError && helpText && (
        <HelpTextArea $hasError={hasError}>{helpText}</HelpTextArea>
      )}
    </StyledTextInput>
  );
}

const INPUT_HEIGHT = {
  tall: 56,
  short: 40,
};

const StyledTextInput = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4px;
`;

const InputContainer = styled.div<{
  $variant: "tall" | "short";
  $hasError?: boolean;
}>`
  display: flex;
  flex-direction: ${({ $variant }) => ($variant === "tall" ? "column" : "row")};
  width: 100%;
  justify-content: ${({ $variant }) =>
    $variant === "tall" ? "center" : "space-between"};
  padding: 0px 16px;
  gap: 8px;
  color: ${({ theme: { neutral } }) => neutral.text.weak};
  height: ${({ $variant }) => INPUT_HEIGHT[$variant]}px;
  border: ${({ $hasError, theme: { border, danger } }) =>
    $hasError && `${border.default} ${danger.text.default}`};
  border-radius: ${({ $variant, theme: { radius } }) =>
    $variant === "tall" ? `${radius.l}` : `${radius.m}`};
  background-color: ${({ $hasError, theme: { neutral } }) =>
    $hasError ? neutral.surface.strong : neutral.surface.bold};
  &:focus-within {
    border: ${({ $hasError, theme: { neutral, border, danger } }) =>
      $hasError
        ? `${border.default} ${danger.text.default}`
        : `${border.default} ${neutral.border.defaultActive}`};
    background-color: ${({ theme: { neutral } }) => neutral.surface.strong};
  }
  &:disabled {
    opacity: ${({ theme: { opacity } }) => opacity.disabled};
    background-color: ${({ theme: { neutral } }) => neutral.surface.bold};
  }
`;

const Input = styled.input`
  display: flex;
  width: 100%;
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

const HelpTextArea = styled.span<{
  $hasError?: boolean;
}>`
  display: flex;
  color: ${({ $hasError, theme: { neutral, danger } }) =>
    $hasError ? danger.text.default : neutral.text.weak};
  font: ${({ theme: { font } }) => font.displayMD12};
  padding: 0px 16px;
`;
