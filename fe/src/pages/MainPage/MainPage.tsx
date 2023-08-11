import Header from "@components/Header";
import { Outlet } from "react-router-dom";
import { styled } from "styled-components";

export default function MainPage() {
  return (
    <StyledMainPage>
      <Header />

      <main>
        <Outlet />
      </main>
    </StyledMainPage>
  );
}

const StyledMainPage = styled.div`
  width: 100%;
  min-height: 100vh;
  padding: 27px 80px;

  main {
    width: 100%;
  }
`;
