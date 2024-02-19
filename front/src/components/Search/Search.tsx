import * as styles from "./Search.styles.tsx";

interface SearchProps {
  search: React.MutableRefObject<string>;
  handleSearchButtonClick: () => void;
  handleEnterPress: (event: React.KeyboardEvent<HTMLInputElement>) => void;
}

const Search = ({
  search,
  handleSearchButtonClick,
  handleEnterPress,
}: SearchProps) => {
  return (
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
  );
};

export default Search;
