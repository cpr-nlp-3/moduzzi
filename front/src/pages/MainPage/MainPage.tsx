import * as styles from "./MainPage.styles.tsx";

import { useState, useRef, useEffect } from "react";

import Button from "@/components/Button/Button.tsx";
import DarkMode from "@/components/DarkMode/DarkMode.tsx";
import Error from "@/components/Error/Error.tsx";
import Evaluation from "@/components/Evaluation/Evaluation.tsx";
import Lectures from "@/components/Lectures/Lectures.tsx";
import Logo from "@/components/Logo/Logo.tsx";
import Search from "@/components/Search/Search.tsx";

interface LecturesInterface {
  subject: string;
  professor: string;
}

const MainPage = () => {
  const [error, setError] = useState<"none" | "error" | "loading">("none");
  const [errorMessage, setErrorMessage] = useState<string>("");
  const [mode, setMode] = useState<"main" | "search" | "detail">("main");
  const [isSubject, setIsSubject] = useState<boolean>(true);
  const [lectures, setLectures] = useState<LecturesInterface[]>([]);
  const search = useRef<string>("");

  const handleSearchButtonClick = () => {
    if (search.current !== "") {
      setMode("search");
      setLectures([{ subject: "자료구조", professor: "장부루" }]);
    }
  };

  const handleEnterPress = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.nativeEvent.isComposing) {
      return;
    }

    if (event.key === "Enter") {
      handleSearchButtonClick();
    }
  };

  const getContent = () => {
    if (error === "error" || error === "loading") {
      return <Error errorMessage={errorMessage} />;
    }

    if (mode === "search") {
      return <Lectures lectures={lectures} setMode={setMode} />;
    } else if (mode === "detail") {
      return (
        <Evaluation
          subject={"자료구조"}
          professor={"장부루"}
          score={4.9}
          details={["교수님이 좋아요", "조교님도 좋아요", "제 기분도 좋아요"]}
        />
      );
    }
  };

  useEffect(() => {
    console.log(
      `%c
  ███╗   ███╗ ██████╗ ██████╗ ██╗   ██╗███████╗███████╗██╗
  ████╗ ████║██╔═══██╗██╔══██╗██║   ██║╚══███╔╝╚══███╔╝██║
  ██╔████╔██║██║   ██║██║  ██║██║   ██║  ███╔╝   ███╔╝ ██║
  ██║╚██╔╝██║██║   ██║██║  ██║██║   ██║ ███╔╝   ███╔╝  ██║
  ██║ ╚═╝ ██║╚██████╔╝██████╔╝╚██████╔╝███████╗███████╗██║
  ╚═╝     ╚═╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚══════╝╚══════╝╚═╝
  `,
      "color:#444eee",
    );
  }, []);

  return (
    <styles.OuterContainer>
      <styles.InnerContainer>
        <Logo />
        <Search
          search={search}
          handleSearchButtonClick={handleSearchButtonClick}
          handleEnterPress={handleEnterPress}
        />
        <Button isSubject={isSubject} setIsSubject={setIsSubject} />
        {getContent()}
        <DarkMode />
      </styles.InnerContainer>
    </styles.OuterContainer>
  );
};

export default MainPage;
