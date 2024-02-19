import styled from "styled-components";

import Fontstyles from "@/styles/Fontstyles.styles";

export const ButtonContainer = styled.div`
  display: flex;
  justify-content: right;
  margin-top: 0.625rem;
  width: 100%;
  height: 2.5rem;
`;

export const Button = styled.div<{ $isAvailable: boolean }>`
  ${(props) => (props.$isAvailable ? Fontstyles.Bold_M : Fontstyles.Medium_M)}
  margin-left: 1.25rem;
  padding-left: 2.5rem;
  width: max-content;
  background-image: ${(props) =>
    props.$isAvailable
      ? 'url("/images/check.png")'
      : props.theme.background === "#ffffff"
        ? 'url("/images/non-check-light.png")'
        : 'url("/images/non-check-dark.png")'};
  background-repeat: no-repeat;
  background-size: 2.5rem 2.5rem;
  line-height: 2.5rem;
  color: ${(props) => (props.$isAvailable ? "#444eee" : props.theme.lightText)};
  cursor: pointer;
`;
