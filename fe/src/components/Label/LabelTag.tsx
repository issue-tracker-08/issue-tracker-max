import { styled } from "styled-components";

export default function LabelTag({
  name,
  fontColor,
  backgroundColor,
}: {
  name: string;
  fontColor: string;
  backgroundColor: string;
}) {
  return (
    <StyledLabelTag $fontColor={fontColor} $backgroundColor={backgroundColor}>
      {name}
    </StyledLabelTag>
  );
}

const StyledLabelTag = styled.span<{
  $fontColor: string;
  $backgroundColor: string;
}>`
  display: inline-block;
  height: 24px;
  padding-inline: 12px;
  background-color: ${({ $backgroundColor }) => $backgroundColor};
  color: ${({ $fontColor }) => $fontColor};
  font: ${({ theme: { font } }) => font.displayMD12};
  border-radius: ${({ theme: { radius } }) => radius.l};
  line-height: 24px;
`;
