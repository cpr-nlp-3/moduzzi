import * as styles from "./Message.styles.tsx";

interface MessageProps {
  errorMessage: string;
}

const Message = ({ errorMessage }: MessageProps) => {
  return <styles.Message>{errorMessage}</styles.Message>;
};

export default Message;
