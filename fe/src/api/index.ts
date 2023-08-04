import { IssueItem, Label, Milestone, User } from "@customTypes/index";
import {
  fetcher,
  fetcherFormDataWithBearer,
  fetcherWithBearer,
} from "./fetcher";

export const postSignup = async (username: string, password: string) => {
  return await fetcher.post("/auth/signup", { loginId: username, password });
};

export const postLogin = async (username: string, password: string) => {
  return await fetcher.post("/auth/login", { loginId: username, password });
};

export const getIssues = async () => {
  return await fetcherWithBearer.get<IssueItem[]>("/issues");
};

export const getIssue = async (issueId: number) => {
  // TODO: specify return type
  return await fetcherWithBearer.get(`/issues/${issueId}`);
};

export const getLabels = async () => {
  return await fetcherWithBearer.get<Label[]>("/labels");
};

export const getMilestones = async () => {
  return await fetcherWithBearer.get<Milestone[]>("/milestones");
};

export const getUsers = async () => {
  return await fetcherWithBearer.get<User[]>("/users");
};

type NewIssueBody = {
  title: string;
  content: string;
  assignees: number[];
  labels: number[];
  milestone: number;
};

export const postIssue = async (body: NewIssueBody) => {
  return await fetcherWithBearer.post<{ issueId: number }>("/issues", body);
};

export const postImage = async (file: File) => {
  const formData = new FormData();
  formData.append("image", file);

  return await fetcherFormDataWithBearer.post<{ fileUrl: string }>(
    "/upload",
    formData
  );
};
