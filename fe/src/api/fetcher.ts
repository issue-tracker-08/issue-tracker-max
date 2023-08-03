import axios from "axios";

export const fetcher = axios.create({
  baseURL: "/api",
  headers: { "Content-Type": "application/json" },
});

export const fetcherWithBearer = axios.create({
  baseURL: "/api",
  headers: { "Content-Type": "application/json" },
});

fetcherWithBearer.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    Promise.reject(error);
  }
);

export const fetcherFormDataWithBearer = axios.create({
  baseURL: "/api",
  headers: { "Content-Type": "multipart/form-data" },
});
