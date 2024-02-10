import * as styles from "./Logo.styles.tsx";

const Logo = () => {
  const handleLogoClick = () => {
    location.href = "/";
  };

  return (
    <styles.Logo src="/images/logo.png" alt="logo" onClick={handleLogoClick} />
  );
};

export default Logo;
