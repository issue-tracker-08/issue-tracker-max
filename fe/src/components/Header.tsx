import moonIcon from "@assets/icon/moon.svg";
import sunIcon from "@assets/icon/sun.svg";
import { styled } from "styled-components";
import Logo from "./common/Logo";
import ToggleSwitch from "./common/ToggleSwitch";

export default function Header() {
  return (
    <StyledHeader>
      <Logo size="large" />
      <div className="global-bar">
        <ToggleSwitch
          onImg={moonIcon}
          offImg={sunIcon}
          onToggle={() => console.log("toggle dark mode")}
        />

        {/* TODO: user object에서 사진 정보 가져오기 */}
        <UserProfileImg
          src="https://avatars.githubusercontent.com/u/79886384?v=4"
          alt="User Profile Picture"
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

const UserProfileImg = styled.img`
  width: 32px;
  height: 32px;
  overflow: hidden;
  border-radius: ${({ theme: { radius } }) => radius.half};
`;
