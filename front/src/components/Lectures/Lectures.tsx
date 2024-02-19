import * as styles from "./Lectures.styles.tsx";

interface LecturesInterface {
  subject: string;
  professor: string;
}

interface LecturesProps {
  lectures: LecturesInterface[];
  setMode: (mode: "main" | "search" | "detail") => void;
}

const Lectures = ({ lectures, setMode }: LecturesProps) => {
  const changeMode = () => {
    setMode("detail");
  };

  return (
    <styles.LecturesContainer>
      {lectures.map((element, index) => (
        <styles.LectureContainer onClick={changeMode} key={index}>
          <styles.Subject>{element.subject}</styles.Subject>
          <styles.Professor>{element.professor}</styles.Professor>
        </styles.LectureContainer>
      ))}
    </styles.LecturesContainer>
  );
};

export default Lectures;
