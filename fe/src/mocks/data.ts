import { faker } from "@faker-js/faker";

export const loginInfo: Record<string, string> = {
  zoeyzoey: "zoeyzoey",
};

export const users = Array.from({ length: 50 }, (_, i) => {
  return {
    userAccountId: i + 1,
    username: faker.internet.userName(),
    profileUrl: faker.image.avatar(),
  };
});

export const issueList = [
  {
    issueNumber: 1,
    isOpen: true,
    title: "메인화면 UI 설계",
    labels: [
      {
        name: "bug",
        fontColor: "#FFF",
        backgroundColor: "#D73A4A",
      },
      {
        name: "docs",
        fontColor: "#FFF",
        backgroundColor: "#2675CA",
      },
    ],
    milestone: "Sprint #1",
    author: {
      id: 1,
      username: "bruni",
      profileUrl: "https://avatars.githubusercontent.com/u/79886384?v=4",
    },
    assignees: [
      {
        username: "bruni",
        profileUrl: "https://avatars.githubusercontent.com/u/79886384?v=4",
      },
    ],
    createdAt: "2023-07-31 18:02:32",
  },
  {
    issueNumber: 2,
    isOpen: true,
    title: "Mock data 설정",
    labels: [
      {
        name: "feat",
        fontColor: "#000",
        backgroundColor: "#FACA02",
      },
    ],
    milestone: "Sprint #2",
    author: {
      id: 2,
      username: "Kakamotobi",
      profileUrl: "https://avatars.githubusercontent.com/u/79886384?v=4",
    },
    assignees: [
      {
        username: "Kakamotobi",
        profileUrl: "https://avatars.githubusercontent.com/u/79886384?v=4",
      },
      {
        username: "Zoey",
        profileUrl: "https://avatars.githubusercontent.com/u/111998760?v=4",
      },
    ],
    createdAt: "2023-07-26 13:22:10",
  },
  {
    issueNumber: 3,
    isOpen: true,
    title: "API 연동",
    labels: [
      {
        name: "feat",
        fontColor: "#000",
        backgroundColor: "#FACA02",
      },
    ],
    milestone: "Sprint #2",
    author: {
      id: 3,
      username: "Zoey",
      profileUrl: "https://avatars.githubusercontent.com/u/79886384?v=4",
    },
    assignees: [
      {
        username: "Kakamotobi",
        profileUrl: "https://avatars.githubusercontent.com/u/79886384?v=4",
      },
      {
        username: "Zoey",
        profileUrl: "https://avatars.githubusercontent.com/u/111998760?v=4",
      },
    ],
    createdAt: "2023-05-25 10:42:40",
  },
  {
    issueNumber: 4,
    isOpen: false,
    title: "프로젝트 초기화",
    labels: [
      {
        name: "feat",
        fontColor: "#000",
        backgroundColor: "#FACA02",
      },
    ],
    milestone: "",
    author: {
      id: 4,
      username: "Kakamotobi",
      profileUrl: "https://avatars.githubusercontent.com/u/79886384?v=4",
    },
    assignees: [
      {
        username: "Kakamotobi",
        profileUrl: "https://avatars.githubusercontent.com/u/79886384?v=4",
      },
    ],
    createdAt: "2022-12-31 10:42:40",
  },
];

export const issueDetails = {
  issueId: 1,
  title: "이슈 제목",
  isOpen: true,
  createdAt: "2023-07-31 11:33:03",
  author: {
    username: faker.internet.userName(),
    profileUrl: faker.image.avatar(),
  },
  content: `
  혹시 차이는 
  
  ![autism](https://github.com/issue-tracker-08/issue-tracker-max/assets/111998760/0072a597-4dd1-452d-b881-15ba0b50d47d) 
  
  뭐야 이거 왜 지금은 돼
  `,
  commentCount: 15,
};

export const issueSidebar = {
  assigneeIds: [1, 5],
  labelIds: [1, 2],
  milestoneId: 1,
};

export const labelList = [
  {
    labelId: 1,
    name: "bug",
    fontColor: "#FFF",
    backgroundColor: "#D73A4A",
    description: "bug fix",
  },
  {
    labelId: 2,
    name: "docs",
    fontColor: "#FFF",
    backgroundColor: "#2675CA",
    description: "documentation",
  },
  {
    labelId: 3,
    name: "feat",
    fontColor: "#000",
    backgroundColor: "#FACA02",
    description: "new feature",
  },
  {
    labelId: 4,
    name: "style",
    fontColor: "#FFF",
    backgroundColor: "#0025E6",
    description: "changes unrelated to source code",
  },
  {
    labelId: 5,
    name: "asdf",
    fontColor: "#FFF",
    backgroundColor: "#0025E6",
    description: "",
  },
  {
    labelId: 6,
    name: "qerqer",
    fontColor: "#000",
    backgroundColor: "#FF3B30",
    description: "",
  },
];

export const openMilestoneList = Array.from({ length: 10 }, (_, i) => {
  return {
    milestoneId: i + 1,
    milestoneName: faker.lorem.words(),
    openIssueCount: faker.number.int(),
    closedIssueCount: faker.number.int(),
    description: faker.lorem.sentence(),
    dueDate: faker.date.future().toISOString().slice(0, 10),
    isOpen: true,
  };
});

export const closedMilestoneList = Array.from({ length: 3 }, (_, i) => {
  return {
    milestoneId: i + 10,
    milestoneName: faker.lorem.words(),
    openIssueCount: faker.number.int(),
    closedIssueCount: faker.number.int(),
    description: faker.lorem.sentence(),
    dueDate: faker.date.future().toISOString().slice(0, 10),
    isOpen: false,
  };
});

export const comment0 = {
  data: Array.from({ length: 10 }, (_, i) => {
    return {
      commentId: 300 + i,
      username: faker.internet.userName(),
      profileUrl: faker.image.avatar(),
      content: faker.lorem.sentences(),
      createdAt: faker.date.recent().toISOString(),
    };
  }),
  hasMore: true,
  nextCursor: 1,
};

export const comment1 = {
  data: Array.from({ length: 10 }, (_, i) => {
    return {
      commentId: 500 + i,
      username: faker.internet.userName(),
      profileUrl: faker.image.avatar(),
      content: faker.lorem.sentences(),
      createdAt: faker.date.recent().toISOString(),
    };
  }),
  hasMore: true,
  nextCursor: 2,
};

export const comment2 = {
  data: Array.from({ length: 5 }, (_, i) => {
    return {
      commentId: 800 + i,
      username: faker.internet.userName(),
      profileUrl: faker.image.avatar(),
      content: faker.lorem.sentences(),
      createdAt: faker.date.recent().toISOString(),
    };
  }),
  hasMore: false,
};
