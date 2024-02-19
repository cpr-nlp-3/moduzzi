import { ThemeProvider } from "@/context/ThemeProvider.tsx";
import MainPage from "@/pages/MainPage/MainPage.tsx";
import GlobalStyles from "@/styles/Globalstyles.styles.tsx";

const App = () => {
  return (
    <ThemeProvider>
      <MainPage />
      <GlobalStyles />
    </ThemeProvider>
  );
};

export default App;
