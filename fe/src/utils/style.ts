// 0 <= opacity <= 1
export const decToHex = (opacity: number) => {
  return Math.floor(opacity * 255)
    .toString(16)
    .toUpperCase()
    .padStart(2, "0");
};
