import moonIcon from "@assets/icon/moon.svg";
import sunIcon from "@assets/icon/sun.svg";
import { useAuth } from "context/authContext";
import { ThemeModeContext } from "context/themeModeContext";
import { useContext } from "react";
import { Link } from "react-router-dom";
import { styled } from "styled-components";
import { Avatar } from "./common/Avatar";
import Button from "./common/Button";
import Logo from "./common/Logo";
import ToggleSwitch from "./common/ToggleSwitch";

export default function Header() {
  const { userInfo, onLogout } = useAuth();
  const { toggleThemeMode } = useContext(ThemeModeContext);

  return (
    <StyledHeader>
      <Link to="/">
        <Logo size="large" />
      </Link>
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
        <Button variant="ghost" size="M" onClick={onLogout}>
          <span className="logout-btn-text">로그아웃</span>
        </Button>
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

    .logout-btn-text {
      color: ${({ theme: { neutral } }) => neutral.text.default};
    }
  }
`;
