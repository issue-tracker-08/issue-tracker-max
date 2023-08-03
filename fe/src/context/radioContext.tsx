import { createContext } from "react";

export const RadioContext = createContext<{
  value: number;
  onChange: (value: number) => void;
} | null>(null);
