import styled from "styled-components";

import Fontstyles from "@/styles/Fontstyles.styles";

export const InfoContainer = styled.div`
  display: flex;
  width: 100%;
  height: 3.75rem;
  line-height: 3.75rem;
`;

export const Title = styled.div`
  ${Fontstyles.Bold_XL}
  margin-right: 1.25rem;
`;

export const Content = styled.div`
  ${Fontstyles.Medium_L}
`;
