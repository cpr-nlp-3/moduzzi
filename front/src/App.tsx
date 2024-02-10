import { BrowserRouter, Route, Routes } from "react-router-dom";

import MainPage from "@/pages/MainPage/MainPage.tsx";
import GlobalStyles from "@/styles/Globalstyles.styles";

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainPage />} />
      </Routes>
      <GlobalStyles />
    </BrowserRouter>
  );
};

export default App;
