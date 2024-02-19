import * as styles from "./Info.styles.tsx";

interface InfoProps {
  title: string;
  content: string;
}

const Info = ({ title, content }: InfoProps) => {
  return (
    <styles.InfoContainer>
      <styles.Title>{title}</styles.Title>
      <styles.Content>{content}</styles.Content>
    </styles.InfoContainer>
  );
};

export default Info;
