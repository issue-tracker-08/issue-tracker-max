import {
  ReactNode,
  createContext,
  useContext,
  useEffect,
  useState,
} from "react";

type UserInfo = {
  username: string;
  profileUrl: string;
};

type LoginResponse = {
  accessToken: string;
  expirationTime: number;
  userInfo: UserInfo;
};

type AuthContextType = {
  isLoggedIn: boolean;
  userInfo: UserInfo;
  onLogin: ({ accessToken, expirationTime, userInfo }: LoginResponse) => void;
  onLogout: () => void;
};

const authContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);
  const [userInfo, setUserInfo] = useState<UserInfo>({
    username: "",
    profileUrl: "",
  });

  const onLogin = ({
    accessToken,
    expirationTime,
    userInfo,
  }: LoginResponse) => {
    localStorage.setItem("accessToken", accessToken);
    localStorage.setItem("userInfo", JSON.stringify(userInfo));
    localStorage.setItem("expirationTime", expirationTime.toString());

    setUserInfo(userInfo);
    setIsLoggedIn(true);
  };

  const onLogout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("userInfo");
    localStorage.removeItem("expirationTime");

    setUserInfo({ username: "", profileUrl: "" });
    setIsLoggedIn(false);
  };

  useEffect(() => {
    const expirationTime = localStorage.getItem("expirationTime");
    const userInfo = localStorage.getItem("userInfo");

    if (expirationTime && Date.now() < parseInt(expirationTime, 10)) {
      setIsLoggedIn(true);
      setUserInfo(
        userInfo ? JSON.parse(userInfo) : { username: "", profileUrl: "" }
      );
    } else {
      onLogout();
    }
  }, []);

  return (
    <authContext.Provider value={{ isLoggedIn, userInfo, onLogin, onLogout }}>
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
