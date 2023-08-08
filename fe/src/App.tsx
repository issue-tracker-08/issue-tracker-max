import AuthPage from "@pages/Auth/AuthPage";
import LoginPage from "@pages/Auth/LoginPage";
import SignupPage from "@pages/Auth/SignupPage";
import IssueDetailPage from "@pages/MainPage/IssueDetailPage";
import IssuesPage from "@pages/MainPage/IssuesPage";
import LabelPage from "@pages/MainPage/LabelPage";
import MainPage from "@pages/MainPage/MainPage";
import MilestonePage from "@pages/MainPage/MilestonePage";
import NewIssuePage from "@pages/MainPage/NewIssuePage";
import GlobalStyle from "@styles/GlobalStyle";
import { darkMode, lightMode } from "@styles/designSystem";
import { ProtectedRoute } from "ProtectedRoute";
import { ThemeModeContext } from "context/themeModeContext";
import { useContext } from "react";
import {
  Route,
  RouterProvider,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";
import { ThemeProvider } from "styled-components";

const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route path="/auth" element={<AuthPage />}>
        <Route index element={<LoginPage />} />
        <Route path="signup" element={<SignupPage />} />
      </Route>

      <Route
        path="/"
        element={
          <ProtectedRoute>
            <MainPage />
          </ProtectedRoute>
        }>
        <Route index element={<IssuesPage />} />
        <Route path="issues/:issueId" element={<IssueDetailPage />} />
        <Route path="labels" element={<LabelPage />} />
        <Route path="milestones" element={<MilestonePage />} />
        <Route path="issues/new" element={<NewIssuePage />} />
      </Route>
    </>
  )
);

export default function App() {
  const { themeMode } = useContext(ThemeModeContext);

  return (
    <ThemeProvider theme={themeMode === "light" ? lightMode : darkMode}>
      <GlobalStyle />
      <RouterProvider router={router} />
    </ThemeProvider>
  );
}
