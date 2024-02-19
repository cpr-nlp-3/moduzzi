import * as styles from "./List.styles.tsx";

interface ListInterface {
  subject: string;
  professor: string;
}

interface ListProps {
  list: ListInterface[];
  setMode: (mode: "main" | "search" | "detail") => void;
}

const List = ({ list, setMode }: ListProps) => {
  const changeMode = () => {
    setMode("detail");
  };

  return (
    <styles.ListContainer>
      {list.map((element, index) => (
        <styles.ElementContainer onClick={changeMode} key={index}>
          <styles.Subject>{element.subject}</styles.Subject>
          <styles.Professor>{element.professor}</styles.Professor>
        </styles.ElementContainer>
      ))}
    </styles.ListContainer>
  );
};

export default List;
