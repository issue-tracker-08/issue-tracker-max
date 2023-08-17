import GitHubLoginButton from "@components/Auth/GitHubLoginButton";
import Button from "@components/common/Button";
import TextInput from "@components/common/TextInput";
import useInput from "@hooks/useInput";
import { getGitHubLogin, postLogin, postOAuthUsername } from "api";
import { AxiosError } from "axios";
import { useAuth } from "context/authContext";
import { FormEvent, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import styled from "styled-components";

export default function LoginPage() {
  const { isValid: isValidUsername, ...username } = useInput({
    initialValue: "",
    maxLength: 16,
    minLength: 6,
  });
  const { isValid: isValidPassword, ...password } = useInput({
    initialValue: "",
    maxLength: 16,
    minLength: 6,
  });
  const [errorMessage, setErrorMessage] = useState<string>("");
  const [newOAuthUserEmail, setNewOAuthUserEmail] = useState<string | null>(
    null
  );
  const { onLogin } = useAuth();
  const navigate = useNavigate();

  // GitHub Callback URL (임시토큰 접수)
  useEffect(() => {
    const gitHubLogin = async (code: string) => {
      try {
        const res = await getGitHubLogin(code);

        if (res.status === 200) {
          const { token, user } = res.data;
          onLogin({
            accessToken: token.accessToken,
            userInfo: user,
            expirationTime: token.expirationTime,
          });
          navigate("/");
        } else if (res.status === 202) {
          // GitHub을 통한 최초 로그인 (i.e. username 없음. 등록해야 회원가입 완료.)
          const { email } = res.data;
          setNewOAuthUserEmail(email);
        }
      } catch (error) {
        console.error(error);
      }
    };

    const urlParams = new URLSearchParams(document.location.search);
    const code = urlParams.get("code");
    if (code) {
      gitHubLogin(code);
    }
  });

  const onOAuthUsernameSubmit = async (e: FormEvent) => {
    e.preventDefault();

    try {
      const res = await postOAuthUsername({
        username: username.value,
        email: newOAuthUserEmail as string,
      });
      const { token, user } = res.data;
      onLogin({
        accessToken: token.accessToken,
        userInfo: user,
        expirationTime: token.expirationTime,
      });
      setNewOAuthUserEmail(null);
      navigate("/");
    } catch (error) {
      // TODO
      console.error(error);
    }
  };

  const onIdPasswordLogin = async (e: FormEvent) => {
    e.preventDefault();

    try {
      const res = await postLogin(username.value, password.value);
      const { token, user } = res.data;
      onLogin({
        accessToken: token.accessToken,
        userInfo: user,
        expirationTime: token.expirationTime,
      });
      navigate("/");
    } catch (error) {
      if (error instanceof AxiosError && error.response) {
        const { message } = error.response.data;
        setErrorMessage(message);
      }
    }
  };

  return (
    <>
      {newOAuthUserEmail ? (
        <AuthForm onSubmit={onOAuthUsernameSubmit}>
          <span className="username-registration">
            사용하실 아이디를 등록해주세요
          </span>
          <TextInput
            name="아이디"
            variant="tall"
            hasError={!isValidUsername}
            placeholder="아이디"
            helpText="아이디는 최소 6자리여야 해요!"
            {...username}
          />
          <Button
            variant="container"
            size="XL"
            className="login-btn"
            disabled={!isValidUsername}
            type="submit">
            아이디 등록
          </Button>
        </AuthForm>
      ) : (
        <>
          <GitHubLoginButton />
          <span className="or">or</span>
          <AuthForm onSubmit={onIdPasswordLogin}>
            <TextInput
              name="아이디"
              variant="tall"
              hasError={!isValidUsername}
              placeholder="아이디"
              helpText="아이디는 최소 6자리여야 해요!"
              {...username}
            />
            <TextInput
              name="비밀번호"
              variant="tall"
              hasError={!isValidPassword}
              placeholder="비밀번호"
              helpText="비밀번호는 최소 6자리여야 해요!"
              type="password"
              {...password}
            />
            {errorMessage && <p className="error-message">{errorMessage}</p>}
            <Button
              variant="container"
              size="XL"
              className="login-btn"
              disabled={!isValidUsername || !isValidPassword}
              type="submit">
              아이디로 로그인
            </Button>
          </AuthForm>
          <Link to="/signup">
            <Button variant="ghost" size="XL" className="change-auth-btn">
              아직 계정이 없으신가요? 회원가입
            </Button>
          </Link>
        </>
      )}
    </>
  );
}

export const AuthForm = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  gap: 12px;

  .username-registration {
    font: ${({ theme: { font } }) => font.displayMD16};
    color: ${({ theme: { neutral } }) => neutral.text.default};
  }

  .login-btn {
    font: ${({ theme: { font } }) => font.availableMD20};
  }
`;
