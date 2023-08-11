import AuthPage from "@pages/Auth/AuthPage";
import LoginPage from "@pages/Auth/LoginPage";
import SignupPage from "@pages/Auth/SignupPage";
import IssueDetailPage from "@pages/MainPage/IssueDetailPage";
import IssuesPage from "@pages/MainPage/IssuesPage";
import LabelPage from "@pages/MainPage/LabelPage";
import MainPage from "@pages/MainPage/MainPage";
import MilestonePage from "@pages/MainPage/MilestonePage";
import NewIssuePage from "@pages/MainPage/NewIssuePage";
import NotFoundPage from "@pages/NotFoundPage";
import GlobalStyle from "@styles/GlobalStyle";
import { darkMode, lightMode } from "@styles/designSystem";
import { useAuth } from "context/authContext";
import { ThemeModeContext } from "context/themeModeContext";
import { useContext } from "react";
import {
  Route,
  RouterProvider,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";
import { ThemeProvider, styled } from "styled-components";

export default function App() {
  const { themeMode } = useContext(ThemeModeContext);
  const { isLoggedIn } = useAuth();

  const router = createBrowserRouter(
    createRoutesFromElements(
      <>
        {isLoggedIn ? (
          <Route path="/" element={<MainPage />}>
            <Route index element={<IssuesPage />} />
            <Route path="issues/:issueId" element={<IssueDetailPage />} />
            <Route path="labels" element={<LabelPage />} />
            <Route path="milestones" element={<MilestonePage />} />
            <Route path="issues/new" element={<NewIssuePage />} />
            <Route path="*" element={<NotFoundPage />} />
          </Route>
        ) : (
          <Route path="/" element={<AuthPage />}>
            <Route index element={<LoginPage />} />
            <Route path="signup" element={<SignupPage />} />
            <Route path="*" element={<NotFoundPage />} />
          </Route>
        )}
      </>
    )
  );

  return (
    <ThemeProvider theme={themeMode === "light" ? lightMode : darkMode}>
      <GlobalStyle />
      <StyledApp>
        <RouterProvider router={router} />
      </StyledApp>
    </ThemeProvider>
  );
}

const StyledApp = styled.div`
  width: 100%;
  min-width: 960px;
  max-width: 1440px;
  margin: auto;
`;
