import {
  IssueDetails,
  IssueItem,
  IssueSidebar,
  Label,
  Milestone,
  User,
} from "@customTypes/index";
import {
  fetcher,
  fetcherFormDataWithBearer,
  fetcherWithBearer,
} from "./fetcher";
import {
  EditAssigneesBody,
  EditLabelsBody,
  EditMilestoneBody,
  NewIssueBody,
} from "./type";

export const postSignup = async (username: string, password: string) => {
  return await fetcher.post("/auth/signup", { loginId: username, password });
};

export const postLogin = async (username: string, password: string) => {
  return await fetcher.post("/auth/login", { loginId: username, password });
};

export const getIssues = async () => {
  return await fetcherWithBearer.get<IssueItem[]>("/issues");
};

export const getIssueDetails = async (issueId: number) => {
  return await fetcherWithBearer.get<IssueDetails>(`/issues/${issueId}`);
};

export const putIssueTitle = async (
  issueId: number,
  body: { title: string }
) => {
  return await fetcherWithBearer.put(`/issues/${issueId}/title`, body);
};

export const putIssueIsOpen = async (
  issueId: number,
  body: { isOpen: boolean }
) => {
  return await fetcherWithBearer.put(`/issues/${issueId}/isOpen`, body);
};

export const putIssueContent = async (
  issueId: number,
  body: { content: string }
) => {
  return await fetcherWithBearer.put(`/issues/${issueId}/content`, body);
};

export const putIssueComment = async (
  issueId: number,
  commentId: number,
  body: { content: string }
) => {
  return await fetcherWithBearer.put(
    `/issues/${issueId}/comments/${commentId}`,
    body
  );
};

export const getIssueSidebar = async (issueId: number) => {
  return await fetcherWithBearer.get<IssueSidebar>(
    `/issues/${issueId}/sidebar`
  );
};

export const getLabels = async () => {
  return await fetcherWithBearer.get<Label[]>("/labels");
};

export const postLabel = async (body: {
  name: string;
  description: string;
  fontColor: string;
  backgroundColor: string;
}) => {
  return await fetcherWithBearer.post("/labels", body);
};

export const putLabel = async (
  labelId: number,
  body: {
    name: string;
    description: string;
    fontColor: string;
    backgroundColor: string;
  }
) => {
  return await fetcherWithBearer.put(`/labels/${labelId}`, body);
};

export const deleteLabel = async (labelId: number) => {
  return await fetcherWithBearer.delete(`/labels/${labelId}`);
};

export const getMilestones = async (state: "open" | "closed" = "open") => {
  return await fetcherWithBearer.get<Milestone[]>(`/milestones?state=${state}`);
};

export const getUsers = async () => {
  return await fetcherWithBearer.get<User[]>("/users");
};

export const postIssue = async (body: NewIssueBody) => {
  return await fetcherWithBearer.post<{ issueId: number }>("/issues", body);
};

export const postImage = async (file: File) => {
  const formData = new FormData();
  formData.append("image", file);

  return await fetcherFormDataWithBearer.post<{ fileUrl: string }>(
    "/images/upload",
    formData
  );
};

export const postEditField = async (
  issuesId: number,
  field: "assignees" | "labels" | "milestone",
  body: EditAssigneesBody | EditLabelsBody | EditMilestoneBody
) => {
  return await fetcherWithBearer.post(`/issues/${issuesId}/${field}`, body);
};

export const getComments = async (issueId: number, cursor: number) => {
  return await fetcherWithBearer.get(
    `/issues/${issueId}/comments?cursor=${cursor}`
  );
};

export const postComment = async (
  issueId: number,
  body: { content: string }
) => {
  return await fetcherWithBearer.post(`/issues/${issueId}/comments`, body);
};
