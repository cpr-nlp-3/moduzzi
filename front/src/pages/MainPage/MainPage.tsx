import * as styles from "./MainPage.styles.tsx";

import { useRef, useState } from "react";

import List from "@/components/List/List.tsx";
import Logo from "@/components/Logo/Logo.tsx";

interface ListInterface {
  subject: string;
  professor: string;
}

const MainPage = () => {
  const search = useRef("");
  const [isSubject, setIsSubject] = useState<boolean>(true);
  const [list, setList] = useState<ListInterface[]>([]);

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
        {list.map((element, index) => (
          <List
            subject={element.subject}
            professor={element.professor}
            key={index}
          />
        ))}
      </styles.InnerContainer>
    </styles.OuterContainer>
  );
};

export default MainPage;
