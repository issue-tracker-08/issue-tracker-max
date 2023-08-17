import Button from "@components/common/Button";

export default function GitHubLoginButton() {
  const gitHubCredentialsLink = `https://github.com/login/oauth/authorize?client_id=${
    import.meta.env.GITHUB_CLIENT_ID
  }&allow_signup=false&scope=read:user,user:email`;

  return (
    <a href={gitHubCredentialsLink}>
      <Button variant="outline" size="XL" className="github-login-btn">
        GitHub 계정으로 로그인
      </Button>
    </a>
  );
}
