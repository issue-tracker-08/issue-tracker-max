import logo from "@assets/icon/logo.svg";
import { styled } from "styled-components";

type Props = {
  size?: "large" | "medium";
};

const SIZE = {
  medium: {
    width: 200,
    height: 40,
  },
  large: {
    width: 342,
    height: 72,
  },
};

export default function Logo({ size = "medium" }: Props) {
  return (
    <StyledLogo
      src={logo}
      alt="logo"
      width={SIZE[size].width}
      height={SIZE[size].height}
    />
  );
}

const StyledLogo = styled.img`
  filter: ${({ theme: { filter } }) => filter.neutralTextStrong};
`;
