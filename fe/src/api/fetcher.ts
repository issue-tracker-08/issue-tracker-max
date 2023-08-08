import axios from "axios";

const { VITE_APP_API_URL, VITE_PRODUCT_API_URL } = import.meta.env;

const BASE_API_URL =
  process.env.NODE_ENV === "production"
    ? VITE_APP_API_URL
    : VITE_PRODUCT_API_URL;

export const fetcher = axios.create({
  baseURL: `${BASE_API_URL}/api`,
  headers: { "Content-Type": "application/json" },
});

export const fetcherWithBearer = axios.create({
  baseURL: `${BASE_API_URL}/api`,
  headers: { "Content-Type": "application/json" },
});

fetcherWithBearer.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("accessToken");
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
  baseURL: `${BASE_API_URL}/api`,
  headers: { "Content-Type": "multipart/form-data" },
});
