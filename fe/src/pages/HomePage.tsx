import { Outlet } from "react-router-dom";

export default function HomePage() {
  return (
    <div>
      <h1>Home</h1>
      <Outlet />
    </div>
  );
}
