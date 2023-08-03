import styled from "styled-components";

const AVATAR_SIZE = {
  S: "20px",
  M: "32px",
};

export const Avatar = styled.img<{ $size: "S" | "M" }>`
  width: ${({ $size }) => AVATAR_SIZE[$size]};
  height: ${({ $size }) => AVATAR_SIZE[$size]};
  border-radius: ${({ theme: { radius } }) => radius.half};
  overflow: hidden;
`;
