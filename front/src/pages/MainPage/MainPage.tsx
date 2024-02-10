import * as styles from "./MainPage.styles.tsx";

import { useRef, useState } from "react";

import Logo from "@/components/Logo/Logo.tsx";

const MainPage = () => {
  const search = useRef("");
  const [isSubject, setIsSubject] = useState(true);

  const handleSearchButtonClick = () => {
    if (search.current !== "") {
      console.log(search.current);
    }
  };

  const handleEnterPress = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.nativeEvent.isComposing) {
      return;
    }

    if (event.key === "Enter") {
      handleSearchButtonClick();
    }
  };

  return (
    <styles.OuterContainer>
      <styles.InnerContainer>
        <Logo />
        <styles.SearchContainer>
          <styles.SearchInput
            placeholder="과목명, 교수명으로 검색"
            onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
              search.current = event.target.value;
            }}
            onKeyDown={handleEnterPress}
          />
          <styles.SearchButton
            src="/images/search.png"
            alt="search"
            onClick={handleSearchButtonClick}
          />
        </styles.SearchContainer>
        <styles.ButtonContainer>
          <styles.Button
            $isAvailable={isSubject}
            onClick={() => {
              setIsSubject(true);
            }}
          >
            과목명
          </styles.Button>
          <styles.Button
            $isAvailable={!isSubject}
            onClick={() => {
              setIsSubject(false);
            }}
          >
            교수명
          </styles.Button>
        </styles.ButtonContainer>
      </styles.InnerContainer>
    </styles.OuterContainer>
  );
};

export default MainPage;
