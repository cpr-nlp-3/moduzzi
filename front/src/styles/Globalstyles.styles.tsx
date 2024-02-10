import { createGlobalStyle } from "styled-components";
import { reset } from "styled-reset";

const styled = { createGlobalStyle };

const GlobalStyles = styled.createGlobalStyle`
  ${reset}

  @font-face {
    font-family: "Pretendard";
    src: url(/fonts/PretendardVariable.woff2) format("woff2");
  }
`;

export default GlobalStyles;
