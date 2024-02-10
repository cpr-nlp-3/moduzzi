import styled from "styled-components";

import Fontstyles from "@/styles/Fontstyles.styles";

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 1.5rem;
  padding: 0rem 1.875rem 1.5rem 1.875rem;
  border-bottom: 0.0625rem solid #444eee;
  width: 50rem;
  cursor: pointer;
`;

export const Subject = styled.div`
  ${Fontstyles.Bold_L}
  width: 100%;
  height: 3rem;
  line-height: 3rem;
`;

export const Professor = styled.div`
  ${Fontstyles.Medium_M}
  width: 100%;
  height: 2.25rem;
  line-height: 2.25rem;
`;
