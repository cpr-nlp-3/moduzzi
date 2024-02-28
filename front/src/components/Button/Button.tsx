import * as styles from "./Button.styles.tsx";

interface ButtonProps {
  isSubject: boolean;
  setIsSubject: (isSubject: boolean) => void;
  handleSearchButtonClick: () => void;
}

const Button = ({
  isSubject,
  setIsSubject,
  handleSearchButtonClick,
}: ButtonProps) => {
  return (
    <styles.ButtonContainer>
      <styles.Button
        $isAvailable={isSubject}
        onClick={() => {
          if (!isSubject) {
            handleSearchButtonClick();
          }

          setIsSubject(true);
        }}
      >
        과목명
      </styles.Button>
      <styles.Button
        $isAvailable={!isSubject}
        onClick={() => {
          if (isSubject) {
            handleSearchButtonClick();
          }

          setIsSubject(false);
        }}
      >
        교수명
      </styles.Button>
    </styles.ButtonContainer>
  );
};

export default Button;
