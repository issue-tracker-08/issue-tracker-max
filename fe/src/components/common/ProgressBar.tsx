import styled from "styled-components";

export default function ProgressBar({
  name,
  openCount,
  closeCount,
}: {
  name: string;
  openCount: number;
  closeCount: number;
}) {
  const percentage = Math.round((closeCount / (openCount + closeCount)) * 100);

  return (
    <Wrapper>
      <progress id={name} max={100} value={percentage} />
      <label htmlFor={name}>{name}</label>
    </Wrapper>
  );
}

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;

  progress {
    width: 100%;
    appearance: none;
    height: 10px;

    &::-webkit-progress-bar {
      border-radius: ${({ theme: { radius } }) => radius.l};
      background-color: ${({ theme: { neutral } }) => neutral.surface.bold};
    }

    &::-webkit-progress-value {
      border-radius: ${({ theme: { radius } }) =>
        `${radius.l} 0 0 ${radius.l}`};
      background-color: ${({ theme: { brand } }) => brand.surface.default};
    }
  }

  label {
    font: ${({ theme: { font } }) => font.displayMD12};
    color: ${({ theme: { neutral } }) => neutral.text.strong};
  }
`;
