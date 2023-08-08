import { useAuth } from "context/authContext";
import { Navigate } from "react-router-dom";

export const ProtectedRoute = ({ children }: { children: React.ReactNode }) => {
  const { isLoggedIn } = useAuth();

  return isLoggedIn ? children : <Navigate to="/auth" replace />;
};
