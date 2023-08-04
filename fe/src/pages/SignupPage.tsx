import Button from "@components/common/Button";
import TextInput from "@components/common/TextInput";
import useInput from "@hooks/useInput";
import { postSignup } from "api";
import { AxiosError } from "axios";
import { useState } from "react";
import { Link } from "react-router-dom";
import { AuthForm } from "./LoginPage";

export default function SignupPage() {
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

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      await postSignup(username.value, password.value);
    } catch (error) {
      if (error instanceof AxiosError && error.response) {
        const { message } = error.response.data;
        setErrorMessage(message);
      }
    }
  };

  return (
    <>
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
          size="L"
          className="login-btn"
          disabled={!isValidUsername || !isValidPassword}
          type="submit">
          회원가입
        </Button>
      </AuthForm>
      <Link to="/auth">
        <Button variant="ghost" size="M" className="change-auth-btn">
          이미 계정이 있으신가요? 로그인
        </Button>
      </Link>
    </>
  );
}