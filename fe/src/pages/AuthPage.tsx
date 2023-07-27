import { Outlet } from "react-router-dom";

export default function AuthPage() {
  return (
    <div>
      <h1>Auth</h1>
      <Outlet />
    </div>
  );
}
