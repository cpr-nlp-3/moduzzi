import * as styles from "./MainPage.styles.tsx";

import { useState, useRef } from "react";

import Button from "@/components/Button/Button.tsx";
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
      setLectures([
        { subject: "과목1", professor: "교수1" },
        { subject: "과목2", professor: "교수2" },
      ]);
      setMode("search");
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
            subject={"한원준"}
            professor={"아무런 내용"}
            score={2.5}
            details={["asdf", "Asdfasdf", "asdfasdfasdf"]}
          />
        )}
      </styles.InnerContainer>
    </styles.OuterContainer>
  );
};

export default MainPage;
