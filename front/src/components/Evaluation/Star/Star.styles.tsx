import styled from "styled-components";

import Fontstyles from "@/styles/Fontstyles.styles.tsx";

export const StarContainer = styled.div`
  display: flex;
  gap: 1.25rem;
  flex-direction: column;
  width: 100%;
`;

export const Title = styled.div`
  ${Fontstyles.Bold_XL}
  height: 3.75rem;
  line-height: 3.75rem;
  color: ${(props) => props.theme.thickText};
`;

export const Content = styled.div`
  display: flex;
  position: relative;
`;

export const Score = styled.div`
  ${Fontstyles.Medium_L}
  height: 3rem;
  line-height: 3rem;
  color: ${(props) => props.theme.lightText};
`;

export const FloolStarContainer = styled.div`
  display: flex;
  overflow: hidden;
  position: absolute;
  left: 5rem;
  width: 15rem;
  height: 3rem;
`;

export const FloorStar = styled.img`
  padding: 0.25rem;
  width: 3rem;
  height: 3rem;
`;

export const CeilStarContainer = styled.div<{ $score: number }>`
  display: flex;
  overflow: hidden;
  position: absolute;
  left: 5rem;
  width: ${(props) => `${(props.$score / 5) * 15}rem`};
  height: 3rem;
`;

export const CeilStar = styled.img`
  padding: 0.25rem;
  width: 3rem;
  height: 3rem;
`;
