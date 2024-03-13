import {
  createContext,
  useState,
  useLayoutEffect,
  type ReactNode,
} from "react";
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

  useLayoutEffect(() => {
    if (window.matchMedia("(prefers-color-scheme: dark)").matches) {
      setTheme("dark");
    } else {
      setTheme("light");
    }
  }, []);

  return (
    <ThemeContext.Provider value={{ theme, setTheme }}>
      <StyledProvider theme={themeObject}>{children}</StyledProvider>
    </ThemeContext.Provider>
  );
};
