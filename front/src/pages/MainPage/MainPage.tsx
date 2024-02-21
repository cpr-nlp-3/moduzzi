import * as styles from "./MainPage.styles.tsx";

import { useState, useRef, useEffect } from "react";

import Button from "@/components/Button/Button.tsx";
import DarkMode from "@/components/DarkMode/DarkMode.tsx";
import Evaluation from "@/components/Evaluation/Evaluation.tsx";
import Lectures from "@/components/Lectures/Lectures.tsx";
import Logo from "@/components/Logo/Logo.tsx";
import Search from "@/components/Search/Search.tsx";

interface LecturesInterface {
  subject: string;
  professor: string;
}

const MainPage = () => {
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
        {mode === "search" && (
          <Lectures lectures={lectures} setMode={setMode} />
        )}
        {mode === "detail" && (
          <Evaluation
            subject={"자료구조"}
            professor={"장부루"}
            score={4.9}
            details={["교수님이 좋아요", "조교님도 좋아요", "제 기분도 좋아요"]}
          />
        )}
        <DarkMode />
      </styles.InnerContainer>
    </styles.OuterContainer>
  );
};

export default MainPage;
