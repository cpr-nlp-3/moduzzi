import Details from "./Details/Details.tsx";
import * as styles from "./Evaluation.styles.tsx";
import Info from "./Info/Info.tsx";
import Star from "./Star/Star.tsx";

interface EvaluationProps {
  subject: string;
  professor: string;
  score: number;
  details: string[];
}

const Evaluation = ({
  subject,
  professor,
  score,
  details,
}: EvaluationProps) => {
  return (
    <styles.EvaluaionContainer>
      <Info title="과목명" content={subject} />
      <Info title="교수명" content={professor} />
      <Star score={score} />
      <Details details={details} />
    </styles.EvaluaionContainer>
  );
};

export default Evaluation;
