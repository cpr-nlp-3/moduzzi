import * as styles from "./Details.styles.tsx";

interface DetailsProps {
  details: string[];
}

const Details = ({ details }: DetailsProps) => {
  return (
    <styles.DetailsContainer>
      <styles.Title>강의평 요약</styles.Title>
      <styles.Content>
        {details.map((element, index) => (
          <styles.Detail key={index}>{`# ${element}`}</styles.Detail>
        ))}
      </styles.Content>
    </styles.DetailsContainer>
  );
};

export default Details;
