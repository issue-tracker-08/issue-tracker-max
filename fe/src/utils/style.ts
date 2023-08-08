export const decToHex = (opacity: number) => {
  if (opacity < 0 || opacity > 1)
    throw Error("Opacity needs to be between 0 and 1 (inclusive).");

  return Math.floor(opacity * 255)
    .toString(16)
    .toUpperCase()
    .padStart(2, "0");
};
