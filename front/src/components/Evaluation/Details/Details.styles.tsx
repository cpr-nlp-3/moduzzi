import styled from "styled-components";

import Fontstyles from "@/styles/Fontstyles.styles.tsx";

export const DetailsContainer = styled.div`
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
  gap: 0.625rem;
  flex-wrap: wrap;
`;

export const Detail = styled.div`
  ${Fontstyles.Medium_L}
  padding: 0rem 1rem;
  overflow: hidden;
  height: 2.5rem;
  background-color: rgba(68, 78, 238, 0.5);
  line-height: 2.5rem;
  color: #ffffff;
  border-radius: 1.25rem;
`;
