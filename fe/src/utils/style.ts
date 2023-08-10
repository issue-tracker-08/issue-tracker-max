export const decToHexOpacity = (opacity: number) => {
  if (opacity < 0 || opacity > 1)
    throw Error("Opacity needs to be between 0 and 1 (inclusive).");

  return Math.floor(opacity * 255)
    .toString(16)
    .toUpperCase()
    .padStart(2, "0");
};

export const isValidHexColor = (string: string) => {
  return /^#([0-9A-F]{3}){1,2}$/i.test(string);
};

export const generateRandomHexColor = () => {
  return `#${Math.floor(Math.random() * 16777215)
    .toString(16)
    .padStart(6, "0")
    .toUpperCase()}`;
};

export const isHexColorLight = (hex: string) => {
  if (!isValidHexColor) throw Error(`${hex} is not a valid hex color`);

  const parsedHex = hex.replace("#", "");
  const r = parseInt(parsedHex.substring(0, 0 + 2), 16);
  const g = parseInt(parsedHex.substring(2, 2 + 2), 16);
  const b = parseInt(parsedHex.substring(4, 4 + 2), 16);
  const brightness = (r * 299 + g * 587 + b * 114) / 1000;
  return brightness > 155;
};
