import { Label as LabelType } from "@customTypes/index";
import { styled } from "styled-components";

export default function LabelTag({
  label: { name, fontColor, backgroundColor },
}: {
  label: LabelType;
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
  height: 24px;
  padding-inline: 12px;
  background-color: ${({ $backgroundColor }) => $backgroundColor};
  color: ${({ $fontColor }) => $fontColor};
  font: ${({ theme: { font } }) => font.displayMD12};
  border-radius: ${({ theme: { radius } }) => radius.l};
  line-height: 24px;
`;
