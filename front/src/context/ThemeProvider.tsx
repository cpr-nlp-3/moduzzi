import { createContext, useState, type ReactNode } from "react";
import { ThemeProvider as StyledProvider } from "styled-components";

import { lightTheme, darkTheme } from "@/styles/Theme.ts";

export const ThemeContext = createContext({
  theme: "light",
  setTheme: (_theme: "light" | "dark") => {},
});

interface ThemeProviderProps {
  children: ReactNode;
}

export const ThemeProvider = ({ children }: ThemeProviderProps) => {
  const [theme, setTheme] = useState("light");
  const themeObject = theme === "light" ? lightTheme : darkTheme;

  return (
    <ThemeContext.Provider value={{ theme, setTheme }}>
      <StyledProvider theme={themeObject}>{children}</StyledProvider>
    </ThemeContext.Provider>
  );
};
