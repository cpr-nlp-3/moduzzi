import styled from "styled-components";

export const DarkMode = styled.div`
  position: fixed;
  right: 2rem;
  bottom: 2rem;
  width: 3rem;
  height: 3rem;
  background-image: ${(props) =>
    props.theme.background === "#ffffff"
      ? 'url("/images/light-mode.png")'
      : 'url("/images/dark-mode.png")'};
  background-repeat: no-repeat;
  background-size: 3rem 3rem;
  cursor: pointer;
`;
