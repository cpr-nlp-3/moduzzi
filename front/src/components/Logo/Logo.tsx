import * as styles from "./Logo.styles.tsx";

const Logo = () => {
  const onLogo = () => {
    location.href = "/";
  };

  return <styles.Logo src="/images/logo.png" alt="logo" onClick={onLogo} />;
};

export default Logo;
