import logo from "@assets/icon/logo.svg";

type Props = {
  size?: "large" | "medium";
};

export default function Logo({ size = "medium" }: Props) {
  const width = {
    medium: 200,
    large: 342,
  };
  const height = {
    medium: 40,
    large: 72,
  };

  return (
    <img src={logo} alt="logo" width={width[size]} height={height[size]} />
  );
}
