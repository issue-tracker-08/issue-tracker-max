import { ReactNode, createContext, useState } from "react";

export const ThemeModeContext = createContext<{
  themeMode: "light" | "dark";
  toggleThemeMode: () => void;
}>({ themeMode: "light", toggleThemeMode: () => {} });

export const ThemeModeProvider = ({ children }: { children: ReactNode }) => {
  const [themeMode, setThemeMode] = useState<"light" | "dark">("light");

  const toggleThemeMode = () => {
    setThemeMode((prev) => {
      return prev === "light" ? "dark" : "light";
    });
  };

  return (
    <ThemeModeContext.Provider value={{ themeMode, toggleThemeMode }}>
      {children}
    </ThemeModeContext.Provider>
  );
};
