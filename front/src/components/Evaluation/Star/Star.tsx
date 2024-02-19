import * as styles from "./Star.styles.tsx";

interface StarProps {
  score: number;
}

const Star = ({ score }: StarProps) => {
  return (
    <styles.StarContainer>
      <styles.Title>평균 별점</styles.Title>
      <styles.Content>
        <styles.Score>{score.toFixed(2)}</styles.Score>
        <styles.FloolStarContainer>
          <styles.FloorStar src="/images/gray-star.png" />
          <styles.FloorStar src="/images/gray-star.png" />
          <styles.FloorStar src="/images/gray-star.png" />
          <styles.FloorStar src="/images/gray-star.png" />
          <styles.FloorStar src="/images/gray-star.png" />
        </styles.FloolStarContainer>
        <styles.CeilStarContainer $score={score}>
          <styles.CeilStar src="/images/yellow-star.png" />
          <styles.CeilStar src="/images/yellow-star.png" />
          <styles.CeilStar src="/images/yellow-star.png" />
          <styles.CeilStar src="/images/yellow-star.png" />
          <styles.CeilStar src="/images/yellow-star.png" />
        </styles.CeilStarContainer>
      </styles.Content>
    </styles.StarContainer>
  );
};

export default Star;
