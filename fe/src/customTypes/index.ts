export type Label = {
  labelId: number;
  name: string;
  fontColor: string;
  backgroundColor: string;
};

export type Milestone = {
  milestoneId: number;
  milestoneName: string;
  openIssueCount: number;
  closedIssueCount: number;
};

export type Assignee = {
  username: string;
  profileUrl: string;
};

export type IssueItem = {
  issueNumber: number;
  isOpen: boolean;
  title: string;
  authorName: string;
  createdAt: string;
  labels?: Label[];
  milestone?: string;
  assignees?: Assignee[];
};

export type User = {
  userAccountId: number;
  username: string;
  profileUrl: string;
};
