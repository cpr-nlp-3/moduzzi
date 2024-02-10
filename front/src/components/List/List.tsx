import * as styles from "./List.styles.tsx";

interface ListProps {
  subject: string;
  professor: string;
}

const List = ({ subject, professor }: ListProps) => {
  return (
    <styles.Container>
      <styles.Subject>{subject}</styles.Subject>
      <styles.Professor>{professor}</styles.Professor>
    </styles.Container>
  );
};

export default List;
