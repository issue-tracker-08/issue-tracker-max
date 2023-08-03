import Button from "@components/common/Button";
import TextInput from "@components/common/TextInput";
import useInput from "@hooks/useInput";
import { postLogin } from "api";
import { AxiosError } from "axios";
import { useAuth } from "context/authContext";
import { useState } from "react";
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
  const { onLogin } = useAuth();
  const navigate = useNavigate();

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      const {
        data: { token, user },
      } = await postLogin(username.value, password.value);
      onLogin({ accessToken: token.accessToken, userInfo: user });
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
      <Button variant="outline" size="XL" className="github-login-btn">
        GitHub 계정으로 로그인
      </Button>
      <span className="or">or</span>
      <AuthForm onSubmit={onSubmit}>
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
      <Link to="/auth/signup">
        <Button variant="ghost" size="XL" className="change-auth-btn">
          아직 계정이 없으신가요? 회원가입
        </Button>
      </Link>
    </>
  );
}

export const AuthForm = styled.form`
  display: flex;
  flex-direction: column;
  width: 100%;
  gap: 12px;

  .login-btn {
    font: ${({ theme: { font } }) => font.availableMD20};
  }
`;
