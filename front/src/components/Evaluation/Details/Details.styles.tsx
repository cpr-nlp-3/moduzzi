import styled from "styled-components";

import Fontstyles from "@/styles/Fontstyles.styles";

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
`;

export const Content = styled.div`
  display: flex;
  gap: 0.625rem;
  flex-wrap: wrap;
`;

export const Detail = styled.div`
  ${Fontstyles.Medium_L}
  padding: 0rem 1rem;
  height: 2.5rem;
  background-color: #444eee;
  line-height: 2.5rem;
  color: #ffffff;
  opacity: 0.5;
  border-radius: 1.25rem;
`;
