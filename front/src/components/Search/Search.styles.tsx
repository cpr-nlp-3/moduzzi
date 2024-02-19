import styled from "styled-components";

import Fontstyles from "@/styles/Fontstyles.styles";

export const SearchContainer = styled.div`
  display: flex;
  margin-top: 1.875rem;
  border: 0.3125rem solid #444eee;
  width: 50rem;
  height: 5rem;
  border-radius: 2.5rem;
`;

export const SearchInput = styled.input`
  ${Fontstyles.Medium_XL}
  padding-left: 1.875rem;
  width: 45rem;
  height: 100%;
  line-height: 100%;
`;

export const SearchButton = styled.img`
  padding: 0.625rem 1.25rem 0.625rem 0.625rem;
  width: 5rem;
  height: 4.375rem;
  cursor: pointer;
`;
