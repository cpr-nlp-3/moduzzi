import * as styles from "./DarkMode.styles.tsx";

import useTheme from "@/hooks/useTheme.ts";

const DarkMode = () => {
  const { handleTheme } = useTheme();

  return <styles.DarkMode onClick={handleTheme} />;
};

export default DarkMode;
