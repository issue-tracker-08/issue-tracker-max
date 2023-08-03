import moonIcon from "@assets/icon/moon.svg";
import sunIcon from "@assets/icon/sun.svg";
import { useAuth } from "context/authContext";
import { ThemeModeContext } from "context/themeModeContext";
import { useContext } from "react";
import { styled } from "styled-components";
import { Avatar } from "./common/Avatar";
import Logo from "./common/Logo";
import ToggleSwitch from "./common/ToggleSwitch";

export default function Header() {
  const { userInfo } = useAuth();
  const { toggleThemeMode } = useContext(ThemeModeContext);

  return (
    <StyledHeader>
      <Logo size="large" />
      <div className="global-bar">
        <ToggleSwitch
          onImg={moonIcon}
          offImg={sunIcon}
          onToggle={toggleThemeMode}
        />
        <Avatar
          src={userInfo.profileUrl}
          alt={`${userInfo.username}-avatar`}
          $size="M"
        />
      </div>
    </StyledHeader>
  );
}

const StyledHeader = styled.header`
  margin-bottom: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  .global-bar {
    display: flex;
    align-items: center;
    gap: 20px;
  }
`;
