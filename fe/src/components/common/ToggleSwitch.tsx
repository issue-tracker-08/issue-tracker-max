import { MouseEvent } from "react";
import { styled } from "styled-components";

export default function ToggleSwitch({
  offImg,
  onImg,
  onToggle,
}: {
  offImg?: string;
  onImg?: string;
  onToggle: (evt: MouseEvent) => void;
}) {
  return (
    <StyledToggleSwitch>
      <Checkbox type="checkbox" onClick={onToggle} $onImg={onImg ?? ""} />
      <Slider className="slider" $offImg={offImg ?? ""} />
    </StyledToggleSwitch>
  );
}

const StyledToggleSwitch = styled.label`
  position: relative;
`;

const Checkbox = styled.input<{ $onImg: string }>`
  width: 0;
  height: 0;
  position: absolute;
  opacity: 0;

  &:checked + .slider {
    background-color: ${({ theme: { palette } }) => palette.blue};
  }

  &:checked + .slider::before {
    background: ${({ $onImg }) =>
      `url(${$onImg}) no-repeat center center / cover`};
    background-color: ${({ theme: { colors } }) => colors.grey50};
    transform: translateX(18px);
  }
`;

const Slider = styled.span<{ $offImg: string }>`
  width: 44px;
  height: 26px;
  display: block;
  position: relative;
  background-color: ${({ theme: { colors } }) => colors.grey400};
  border-radius: 20px;
  transition: 400ms;
  cursor: pointer;

  // Switch
  &::before {
    content: "";
    background: ${({ $offImg }) =>
      `url(${$offImg}) no-repeat center center / cover`};
    background-color: ${({ theme: { colors } }) => colors.grey50};
    height: 22px;
    width: 22px;
    position: absolute;
    top: 2px;
    left: 2px;
    background-color: #fff;
    transition: 400ms;
    border-radius: 50%;
  }
`;
