import { decToHexOpacity } from "@utils/style";

const colors = {
  grey50: "#FEFEFE",
  grey100: "#F7F7FC",
  grey200: "#EFF0F6",
  grey300: "#D9DBE9",
  grey400: "#BEC1D5",
  grey500: "#A0A3BD",
  grey600: "#6E7191",
  grey700: "#4E4B66",
  grey800: "#2A2A44",
  grey900: "#14142B",
  blue: "#007AFF",
  navy: "#0025E6",
  red: "#FF3B30",
};

const opacity = {
  hover: 0.8,
  press: 0.64,
  disabled: 0.32,
  transparent: 0.04,
};

const radius = {
  m: "12px",
  l: "16px",
  half: "50%",
};

const border = {
  default: `1px solid`,
  icon: `1.6px solid`,
  dashed: `1px dashed`,
};

const font = {
  displayBold32: "700 32px Pretendard, sans-serif",
  displayBold24: "700 24px Pretendard, sans-serif",
  displayBold20: "700 20px Pretendard, sans-serif",
  displayBold16: "700 16px Pretendard, sans-serif",
  displayBold14: "700 14px Pretendard, sans-serif",
  displayBold12: "700 12px Pretendard, sans-serif",
  displayMD20: "500 20px Pretendard, sans-serif",
  displayMD16: "500 16px Pretendard, sans-serif",
  displayMD12: "500 12px Pretendard, sans-serif",
  selectedBold20: "700 20px Pretendard, sans-serif",
  selectedBold16: "700 16px Pretendard, sans-serif",
  selectedBold12: "700 12px Pretendard, sans-serif",
  availableMD20: "500 20px Pretendard, sans-serif",
  availableMD16: "500 16px Pretendard, sans-serif",
  availableMD12: "500 12px Pretendard, sans-serif",
};

export const lightMode = {
  neutral: {
    text: {
      weak: colors.grey600,
      default: colors.grey700,
      strong: colors.grey900,
    },
    surface: {
      default: colors.grey100,
      bold: colors.grey200,
      strong: colors.grey50,
    },
    border: {
      default: colors.grey300,
      defaultActive: colors.grey900,
    },
  },
  brand: {
    text: {
      weak: colors.blue,
      default: colors.grey50,
    },
    surface: {
      weak: colors.grey50,
      default: colors.blue,
    },
    border: {
      default: colors.blue,
    },
  },
  danger: {
    text: {
      default: colors.red,
    },
    border: {
      default: colors.red,
    },
  },
  palette: {
    blue: colors.blue,
    navy: colors.navy,
    red: colors.red,
  },
  boxShadow: `0px 0px 8px 0px ${colors.grey900}${decToHexOpacity(
    opacity.transparent
  )}`,
  colors,
  border,
  radius,
  opacity,
  font,
  filter: {
    neutralTextStrong:
      "brightness(0) saturate(100%) invert(7%) sepia(32%) saturate(1177%) hue-rotate(201deg) brightness(95%) contrast(97%);",
    neutralTextDefault:
      "brightness(0) saturate(100%) invert(28%) sepia(15%) saturate(1004%) hue-rotate(207deg) brightness(91%) contrast(82%);",
    brandTextDefault:
      "brightness(0) saturate(100%) invert(100%) sepia(6%) saturate(2%) hue-rotate(48deg) brightness(105%) contrast(99%);",
    brandTextWeak:
      "brightness(0) saturate(100%) invert(29%) sepia(66%) saturate(2882%) hue-rotate(201deg) brightness(103%) contrast(105%);",
    dangerTextDefault:
      "brightness(0) saturate(100%) invert(44%) sepia(47%) saturate(7165%) hue-rotate(341deg) brightness(98%) contrast(106%);",
  },
};

export const darkMode = {
  neutral: {
    text: {
      weak: colors.grey500,
      default: colors.grey400,
      strong: colors.grey50,
    },
    surface: {
      default: colors.grey900,
      bold: colors.grey700,
      strong: colors.grey800,
    },
    border: {
      default: colors.grey600,
      defaultActive: colors.grey300,
    },
  },
  brand: {
    text: {
      weak: colors.blue,
      default: colors.grey50,
    },
    surface: {
      weak: colors.grey900,
      default: colors.blue,
    },
    border: {
      default: colors.blue,
    },
  },
  danger: {
    text: {
      default: colors.red,
    },
    border: {
      default: colors.red,
    },
  },
  palette: {
    blue: colors.blue,
    navy: colors.navy,
    red: colors.red,
  },
  boxShadow: `0px 0px 16px 0px ${colors.grey900}${decToHexOpacity(
    opacity.hover
  )}`,
  colors,
  border,
  radius,
  opacity,
  font,
  filter: {
    neutralTextStrong:
      "brightness(0) saturate(100%) invert(100%) sepia(2%) saturate(469%) hue-rotate(289deg) brightness(120%) contrast(99%);",
    neutralTextDefault:
      "brightness(0) saturate(100%) invert(90%) sepia(4%) saturate(1252%) hue-rotate(196deg) brightness(89%) contrast(85%);",
    brandTextDefault:
      "brightness(0) saturate(100%) invert(100%) sepia(6%) saturate(2%) hue-rotate(48deg) brightness(105%) contrast(99%);",
    brandTextWeak:
      "brightness(0) saturate(100%) invert(39%) sepia(89%) saturate(4222%) hue-rotate(199deg) brightness(100%) contrast(112%);",
    dangerTextDefault:
      "brightness(0) saturate(100%) invert(44%) sepia(47%) saturate(7165%) hue-rotate(341deg) brightness(98%) contrast(106%);",
  },
};
