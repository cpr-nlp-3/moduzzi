import styled from "styled-components";

import Fontstyles from "@/styles/Fontstyles.styles";

export const OuterContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100vw;
  height: 100vh;
`;

export const InnerContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
`;

export const SearchContainer = styled.div`
  display: flex;
  margin-top: 1.875rem;
  border: 0.3125rem solid #444eee;
  width: 50rem;
  height: 5rem;
  border-radius: 2.8125rem;
`;

export const SearchInput = styled.input`
  ${Fontstyles.Medium_XL}
  padding-left: 1.875rem;
  width: 43.125rem;
  height: 100%;
  line-height: 100%;
`;

export const SearchButton = styled.img`
  padding: 0.625rem;
  width: 3.75rem;
  height: 3.75rem;
  cursor: pointer;
`;

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
      : 'url("/images/check-none.png")'};
  background-repeat: no-repeat;
  background-size: 2.5rem 2.5rem;
  line-height: 2.5rem;
  color: ${(props) => (props.$isAvailable ? "#444eee" : "#000000")};
  cursor: pointer;
`;
