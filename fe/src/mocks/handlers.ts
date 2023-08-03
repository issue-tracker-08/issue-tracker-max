import { rest } from "msw";
import {
  issueDetail,
  issueList,
  labelList,
  loginInfo,
  milestoneList,
  users,
} from "./data";

type AuthRequestBody = {
  username: string;
  password: string;
};

export const handlers = [
  rest.post("/api/auth/signup", async (req, res, ctx) => {
    const { username, password } = await req.json<AuthRequestBody>();
    const isExist = loginInfo[username];
    loginInfo[username] = password;

    if (isExist) {
      return res(
        ctx.status(400),
        ctx.json({
          errorCode: "DUPLICATED_LOGIN_ID",
          message: "중복된 로그인 아이디가 존재합니다.",
        })
      );
    }

    return res(ctx.status(200));
  }),

  rest.post("/api/auth/login", async (req, res, ctx) => {
    const { username, password } = await req.json<AuthRequestBody>();
    const isExist = loginInfo[username];
    const isCorrectPassword = loginInfo[username] === password;

    if (!isExist) {
      return res(
        ctx.status(404),
        ctx.json({
          errorCode: "USER_NOT_FOUND",
          message: "존재하지 않는 회원입니다.",
        })
      );
    }

    if (!isCorrectPassword) {
      return res(
        ctx.status(400),
        ctx.json({
          errorCode: "FAILED_LOGIN",
          message: "비밀번호가 일치하지 않습니다.",
        })
      );
    }

    return res(
      ctx.status(200),
      ctx.json({
        token: {
          tokenType: "bearer",
          accessToken: "q13t302hv2ht0",
          expiresIn: 48000000,
        },
        user: {
          username: username,
          profileUrl: "https://avatars.githubusercontent.com/u/48426991?v=4",
        },
      })
    );
  }),

  rest.get("/api/issues", async (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(issueList));
  }),

  rest.get("/api/issues/1", async (_req, res, ctx) => {
    return res(ctx.status(200), ctx.json(issueDetail));
  }),

  rest.get("/api/labels", async (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(labelList));
  }),

  rest.get("/api/milestones", async (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(milestoneList));
  }),

  rest.get("/api/users", async (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(users));
  }),

  rest.post("/api/issues", async (req, res, ctx) => {
    const newIssue = await req.json();
    issueList.push({
      issueNumber: issueList.length + 1,
      isOpen: true,
      title: newIssue.title,
      labels: labelList.filter((l) => newIssue.labels.includes(l.labelId)),
      authorName: "currentUser",
      assignees: users.filter((u) =>
        newIssue.assignees.includes(u.userAccountId)
      ),
      milestone: "",
      createdAt: new Date().toISOString(),
    });
    return res(ctx.status(200), ctx.json({ issueId: issueList.length + 1 }));
  }),

  rest.post("/api/upload", async (_, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({ fileUrl: "https://i.imgur.com/1.jpg" })
    );
  }),
];

// TODO: 만료된 토큰에 대한 응답 처리
