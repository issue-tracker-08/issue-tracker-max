import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import browserServiceWorker from "./mocks/browser";

if (process.env.NODE_ENV === "development") {
  browserServiceWorker.start({
    onUnhandledRequest: "warn",
  });
}

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
