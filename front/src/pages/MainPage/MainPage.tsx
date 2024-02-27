import * as styles from "./MainPage.styles.tsx";

import { useState, useRef, useEffect } from "react";

import Button from "@/components/Button/Button.tsx";
import DarkMode from "@/components/DarkMode/DarkMode.tsx";
import Evaluation from "@/components/Evaluation/Evaluation.tsx";
import Lectures from "@/components/Lectures/Lectures.tsx";
import Logo from "@/components/Logo/Logo.tsx";
import Message from "@/components/Message/Message.tsx";
import Search from "@/components/Search/Search.tsx";

interface LecturesInterface {
  subject: string;
  professor: string;
}

interface EvaluationInterface {
  subject: string;
  professor: string;
  score: number;
  details: string[];
}

const MainPage = () => {
  const [error, setError] = useState<"none" | "error" | "loading">("none");
  const [errorMessage, setErrorMessage] = useState<string>("");
  const [mode, setMode] = useState<"main" | "search" | "detail">("main");
  const [isSubject, setIsSubject] = useState<boolean>(true);
  const [lectures, setLectures] = useState<LecturesInterface[]>([]);
  const [evaluation, setEvaluation] = useState<EvaluationInterface>({
    subject: "",
    professor: "",
    score: 0,
    details: [],
  });
  const search = useRef<string>("");

  const handleSearchButtonClick = () => {
    if (search.current !== "") {
      const searchType = isSubject ? "name" : "professor";

      setError("loading");
      setErrorMessage("로딩중...");
      setLectures([]);

      fetch(`/api/course/${searchType}/${search.current}`)
        .then(async (res) => {
          if (res.ok) {
            return await res.json();
          } else {
            throw new Error(`${res.status}`);
          }
        })
        .then((data) => {
          data.forEach((element: any) => {
            setLectures((prev) => [
              ...prev,
              { subject: element.name, professor: element.professor },
            ]);
          });

          setError("none");
          setMode("search");
        })
        .catch((error) => {
          if (error.message === "404") {
            setError("error");
            setErrorMessage("검색 결과가 없습니다.");
          } else {
            setError("none");
            alert("서버에 연결할 수 없습니다!");
          }
        });
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
      return <Message errorMessage={errorMessage} />;
    }

    if (mode === "search") {
      return (
        <Lectures
          lectures={lectures}
          setError={setError}
          setErrorMessage={setErrorMessage}
          setMode={setMode}
          setEvaluation={setEvaluation}
        />
      );
    } else if (mode === "detail") {
      return (
        <Evaluation
          subject={evaluation.subject}
          professor={evaluation.professor}
          score={evaluation.score}
          details={evaluation.details}
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
