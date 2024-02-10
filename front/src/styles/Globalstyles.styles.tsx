import { createGlobalStyle } from "styled-components";
import { reset } from "styled-reset";

const styled = { createGlobalStyle };

const GlobalStyles = styled.createGlobalStyle`
  ${reset}

  input {
    margin: 0rem;
    padding: 0rem;
    border: 0rem;
    background: none;
    outline: none;
  }

  @font-face {
    font-family: "Pretendard";
    src: url(/fonts/PretendardVariable.woff2) format("woff2");
  }
`;

export default GlobalStyles;
