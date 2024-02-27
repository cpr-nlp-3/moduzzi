import * as styles from "./Lectures.styles.tsx";

import React from "react";

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

interface LecturesProps {
  lectures: LecturesInterface[];
  setError: (error: "none" | "error" | "loading") => void;
  setErrorMessage: (errorMessage: string) => void;
  setMode: (mode: "main" | "search" | "detail") => void;
  setEvaluation: (evaluation: EvaluationInterface) => void;
}

const Lectures = ({
  lectures,
  setError,
  setErrorMessage,
  setMode,
  setEvaluation,
}: LecturesProps) => {
  const handleClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    const subject: string = event.currentTarget.children[0].textContent!;
    const professor: string = event.currentTarget.children[1].textContent!;

    setError("loading");
    setErrorMessage("로딩중...");

    fetch(
      `${import.meta.env.VITE_API_URL}/result?name=${subject}&professor=${professor}`,
    )
      .then(async (res) => {
        if (res.ok) {
          return await res.json();
        } else {
          throw new Error(`${res.status}`);
        }
      })
      .then((data) => {
        setEvaluation({
          subject,
          professor,
          score: data.averageRating,
          details: data.data.split("\n"),
        });

        setError("none");
        setMode("detail");
      })
      .catch(() => {
        setError("none");
        alert("서버에 연결할 수 없습니다!");
      });
  };

  return (
    <styles.LecturesContainer>
      {lectures.map((element, index) => (
        <styles.LectureContainer onClick={handleClick} key={index}>
          <styles.Subject>{element.subject}</styles.Subject>
          <styles.Professor>{element.professor}</styles.Professor>
        </styles.LectureContainer>
      ))}
    </styles.LecturesContainer>
  );
};

export default Lectures;
