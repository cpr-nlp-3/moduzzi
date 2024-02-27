import * as styles from "./Error.styles.tsx";

interface ErrorProps {
  errorMessage: string;
}

const Error = ({ errorMessage }: ErrorProps) => {
  return <styles.Error>{errorMessage}</styles.Error>;
};

export default Error;
