import { css } from "styled-components";

const getFontSize = (size: "XL" | "L" | "M" | "S") => {
  switch (size) {
    case "XL":
      return "2.5rem";
    case "L":
      return "2rem";
    case "M":
      return "1.5rem";
    case "S":
      return "1rem";
    default:
      return "1rem";
  }
};

const Fontstyles = {
  Bold_XL: css`
    font-family: "Pretendard";
    font-weight: 700;
    font-size: ${getFontSize("XL")};
  `,
  Bold_L: css`
    font-family: "Pretendard";
    font-weight: 700;
    font-size: ${getFontSize("L")};
  `,
  Bold_M: css`
    font-family: "Pretendard";
    font-weight: 700;
    font-size: ${getFontSize("M")};
  `,
  Bold_S: css`
    font-family: "Pretendard";
    font-weight: 700;
    font-size: ${getFontSize("S")};
  `,
  Medium_XL: css`
    font-family: "Pretendard";
    font-weight: 500;
    font-size: ${getFontSize("XL")};
  `,
  Medium_L: css`
    font-family: "Pretendard";
    font-weight: 500;
    font-size: ${getFontSize("L")};
  `,
  Medium_M: css`
    font-family: "Pretendard";
    font-weight: 500;
    font-size: ${getFontSize("M")};
  `,
  Medium_S: css`
    font-family: "Pretendard";
    font-weight: 500;
    font-size: ${getFontSize("S")};
  `,
};

export default Fontstyles;
