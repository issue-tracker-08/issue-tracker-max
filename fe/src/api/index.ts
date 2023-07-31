import { IssueItem, Label, Milestone } from "@customTypes/index";
import { fetcher, fetcherWithBearer } from "./fetcher";

export const postSignup = async (loginId: string, password: string) => {
  return await fetcher.post("/auth/signup", { loginId, password });
};

export const postLogin = async (loginId: string, password: string) => {
  return await fetcher.post("/auth/login", { loginId, password });
};

export const getIssues = async () => {
  return await fetcherWithBearer.get<IssueItem[]>("/issues");
};

export const getLabels = async () => {
  return await fetcherWithBearer.get<Label[]>("/labels");
};

export const getMilestones = async () => {
  return await fetcherWithBearer.get<Milestone[]>("/milestones");
};
