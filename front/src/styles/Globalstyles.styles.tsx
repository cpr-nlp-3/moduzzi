import { createGlobalStyle } from "styled-components";
import { reset } from "styled-reset";

const styled = { createGlobalStyle };

const GlobalStyles = styled.createGlobalStyle`
  ${reset}
`;

export default GlobalStyles;
