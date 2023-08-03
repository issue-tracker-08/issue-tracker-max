import { ReactNode, createContext, useContext, useState } from "react";

type UserInfo = {
  username: string;
  profileUrl: string;
};

type LoginResponse = {
  accessToken: string;
  userInfo: UserInfo;
};

type AuthContextType = {
  isLoggedIn: boolean;
  userInfo: UserInfo;
  onLogin: ({ accessToken, userInfo }: LoginResponse) => void;
};

const authContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);
  const [userInfo, setUserInfo] = useState<UserInfo>({
    username: "",
    profileUrl: "",
  });

  const onLogin = ({ accessToken, userInfo }: LoginResponse) => {
    localStorage.setItem("accessToken", accessToken);
    setUserInfo(userInfo);
    setIsLoggedIn(true);
  };

  return (
    <authContext.Provider value={{ isLoggedIn, userInfo, onLogin }}>
      {children}
    </authContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(authContext);

  if (!context) {
    throw new Error("Cannot find UserProvider");
  }

  return context;
};
