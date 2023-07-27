import AuthPage from "@pages/AuthPage";
import Dashboard from "@pages/Dashboard";
import HomePage from "@pages/HomePage";
import LoginPage from "@pages/LoginPage";
import SignupPage from "@pages/SignupPage";
import GlobalStyle from "@styles/GlobalStyle";
import { darkMode, lightMode } from "@styles/designSystem";
import { useState } from "react";
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
        <Route path="login" element={<LoginPage />} />
        <Route path="signup" element={<SignupPage />} />
      </Route>

      <Route path="/" element={<HomePage />}>
        {/* <Header /> */}
        <Route path="dashboard" element={<Dashboard />} />
      </Route>
    </>
  )
);
export default function App() {
  const [themeMode, setThemeMode] = useState<"light" | "dark">("light");

  const toggleThemeMode = () => {
    setThemeMode((prev) => {
      return prev === "light" ? "dark" : "light";
    });
  };

  return (
    <ThemeProvider theme={themeMode === "light" ? lightMode : darkMode}>
      <GlobalStyle />
      <RouterProvider router={router} />
    </ThemeProvider>
  );
}
