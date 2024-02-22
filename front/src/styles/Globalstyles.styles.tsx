import { createGlobalStyle } from "styled-components";
import { reset } from "styled-reset";

const styled = { createGlobalStyle };

const GlobalStyles = styled.createGlobalStyle`
  ${reset}

  * {
    box-sizing: border-box;
  }

  body {
    background-color: ${(props) => props.theme.background};
  }

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

  html {
    font-size: 20px;
  }

  @media (max-width: 1600px) {
    html {
      font-size: 18px;
    }
  }

  @media (max-width: 1366px) {
    html {
      font-size: 16px;
    }
  }

  @media (max-width: 1280px) {
    html {
      font-size: 14px;
    }
  }

  @media (max-width: 1024px) {
    html {
      font-size: 12px;
    }
  }

  @media (max-width: 768px) {
    html {
      font-size: 10px;
    }
  }

  @media (max-width: 576px) {
    html {
      font-size: 8px;
    }
  }

  @media (max-width: 430px) {
    html {
      font-size: 6px;
    }
  }
`;

export default GlobalStyles;
