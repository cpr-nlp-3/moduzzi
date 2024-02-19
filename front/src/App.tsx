import { ThemeProvider } from "styled-components";

import useTheme from "@/hooks/useTheme";
import MainPage from "@/pages/MainPage/MainPage.tsx";
import GlobalStyles from "@/styles/Globalstyles.styles";
import { lightTheme, darkTheme } from "@/styles/Theme";

const App = () => {
  const { theme } = useTheme();

  return (
    <ThemeProvider theme={theme === "light" ? lightTheme : darkTheme}>
      <MainPage />
      <GlobalStyles />
    </ThemeProvider>
  );
};

export default App;
