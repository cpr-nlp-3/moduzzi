import styled from "styled-components";

import Fontstyles from "@/styles/Fontstyles.styles.tsx";

export const Error = styled.div`
  ${Fontstyles.Bold_XL}
  margin : auto;
  height: 20rem;
  line-height: 20rem;
  color: ${(props) => props.theme.thickText};
`;
