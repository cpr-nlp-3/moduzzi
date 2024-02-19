import * as styles from "./MainPage.styles.tsx";

import { useState } from "react";

import Button from "@/components/Button/Button.tsx";
import List from "@/components/List/List.tsx";
import Logo from "@/components/Logo/Logo.tsx";
import Search from "@/components/Search/Search.tsx";

interface ListInterface {
  subject: string;
  professor: string;
}

const MainPage = () => {
  const [mode, setMode] = useState<"main" | "search" | "detail">("main");
  const [search, setSearch] = useState<string>("");
  const [isSubject, setIsSubject] = useState<boolean>(true);
  const [list, setList] = useState<ListInterface[]>([]);

  const handleSearchButtonClick = () => {
    if (search !== "") {
      setList([
        { subject: "과목1", professor: "교수1" },
        { subject: "과목2", professor: "교수2" },
      ]);
      setMode("search");
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

  const renderContent = () => {
    if (mode === "search") {
      return <List list={list} setMode={setMode} />;
    } else if (mode === "detail") {
      return <List list={list} setMode={setMode} />;
    }
  };

  return (
    <styles.OuterContainer>
      <styles.InnerContainer>
        <Logo />
        <Search
          search={search}
          setSearch={setSearch}
          handleSearchButtonClick={handleSearchButtonClick}
          handleEnterPress={handleEnterPress}
        />
        <Button isSubject={isSubject} setIsSubject={setIsSubject} />
        {renderContent()}
      </styles.InnerContainer>
    </styles.OuterContainer>
  );
};

export default MainPage;
