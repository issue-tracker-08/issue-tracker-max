import Logo from "@components/common/Logo";
import { Outlet } from "react-router-dom";
import styled from "styled-components";

export default function AuthPage() {
  return (
    <StyledAuthPage>
      <Logo size="large" />
      <div className="login-area">
        <Outlet />
      </div>
    </StyledAuthPage>
  );
}

export const StyledAuthPage = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100vw;
  height: 100vh;
  background: ${({ theme: { neutral } }) => neutral.surface.default};

  .login-area {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 320px;
    gap: 24px;
    margin: 48px 0;
  }

  .github-login-btn {
    font: ${({ theme: { font } }) => font.availableMD20};
  }

  .or {
    font: ${({ theme: { font } }) => font.displayMD16};
    color: ${({ theme: { neutral } }) => neutral.text.weak};
  }

  .change-auth-btn {
    font: ${({ theme: { font } }) => font.displayMD16};
    color: ${({ theme: { neutral } }) => neutral.text.default};
  }

  .error-message {
    font: ${({ theme: { font } }) => font.displayMD16};
    color: ${({ theme: { danger } }) => danger.text.default};
  }
`;
