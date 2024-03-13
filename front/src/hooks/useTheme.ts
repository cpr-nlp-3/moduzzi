import { useContext } from "react";

import { ThemeContext } from "@/context/ThemeProvider.tsx";

const useTheme = () => {
  const context = useContext(ThemeContext);
  const { theme, setTheme } = context;

  const handleTheme = () => {
    if (theme === "light") {
      setTheme("dark");
    } else {
      setTheme("light");
    }
  };

  return { theme, handleTheme };
};

export default useTheme;
