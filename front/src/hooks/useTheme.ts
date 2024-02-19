import { useCallback, useEffect, useState } from "react";

const useTheme = () => {
  const [theme, setTheme] = useState<"light" | "dark">("light");

  const handleTheme = useCallback(() => {
    setTheme((curTheme) => (curTheme === "light" ? "dark" : "light"));
  }, []);

  useEffect(() => {
    if (window.matchMedia("(prefers-color-scheme: dark)").matches) {
      setTheme("dark");
    } else {
      setTheme("light");
    }
  }, []);

  return {
    theme,
    handleTheme,
  };
};

export default useTheme;
