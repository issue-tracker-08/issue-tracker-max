import { createContext } from "react";

export type checkFn = (value: number) => boolean;
export type toggleFn = ({
  checked,
  value,
}: {
  checked: boolean;
  value: number;
}) => void;

export const CheckboxContext = createContext<{
  isChecked: checkFn;
  toggleCheck: toggleFn;
} | null>(null);
