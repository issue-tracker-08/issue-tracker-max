export type Label = {
  name: string;
  fontColor: string;
  backgroundColor: string;
};

export type Assignee = {
  username: string;
  profileUrl: string;
};

export type IssueItemType = {
  issueNumber: number;
  isOpen: boolean;
  title: string;
  labels: Label[];
  milestone: string;
  authorName: string;
  assignees: Assignee[];
  createdAt: string;
};
