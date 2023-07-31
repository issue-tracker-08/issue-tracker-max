export type Label = {
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
  labels: Label[];
  milestone: string;
  authorName: string;
  assignees: Assignee[];
  createdAt: string;
};
