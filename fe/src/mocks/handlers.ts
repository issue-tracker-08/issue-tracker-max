import { rest } from "msw";
import {
  closedMilestoneList,
  comment0,
  comment1,
  comment2,
  issueDetails,
  issueList,
  issueSidebar,
  labelList,
  loginInfo,
  openMilestoneList,
  users,
} from "./data";

type AuthRequestBody = {
  loginId: string;
  password: string;
};

export const handlers = [
  rest.post("/api/auth/signup", async (req, res, ctx) => {
    const { loginId, password } = await req.json<AuthRequestBody>();
    const isExist = loginInfo[loginId];
    loginInfo[loginId] = password;

    if (isExist) {
      return res(
        ctx.status(400),
        ctx.json({
          errorCode: "DUPLICATED_LOGIN_ID",
          message: "중복된 로그인 아이디가 존재합니다.",
        })
      );
    }

    return res(ctx.status(201));
  }),

  rest.post("/api/auth/login", async (req, res, ctx) => {
    const { loginId, password } = await req.json<AuthRequestBody>();
    const isExist = loginInfo[loginId];
    const isCorrectPassword = loginInfo[loginId] === password;

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
          expirationTime: 1791720452298,
        },
        user: {
          username: loginId,
          profileUrl: "https://avatars.githubusercontent.com/u/48426991?v=4",
        },
      })
    );
  }),

  rest.get("/api/issues", async (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(issueList));
  }),

  rest.get("/api/issues/:issueId", async (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(issueDetails));
  }),

  rest.put("/api/issues/:issueId/title", async (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.put("/api/issues/:issueId/isOpen", async (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.put("/api/issues", async (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.delete("/api/issues/:issueId", async (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.get("/api/issues/:issueId/sidebar", async (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(issueSidebar));
  }),

  rest.get("/api/labels", async (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(labelList));
  }),

  rest.get("/api/milestones", async (req, res, ctx) => {
    const state = req.url.searchParams.get("state");
    if (state === "open") {
      return res(ctx.status(200), ctx.json(openMilestoneList));
    }
    if (state === "closed") {
      return res(ctx.status(200), ctx.json(closedMilestoneList));
    }

    return res(ctx.status(200), ctx.json(openMilestoneList));
  }),

  rest.get("/api/users", async (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(users));
  }),

  rest.post("/api/issues", async (_, res, ctx) => {
    return res(ctx.status(200), ctx.json({ issueId: issueList.length + 1 }));
  }),

  rest.post("/api/images/upload", async (_, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        fileUrl:
          "https://github.com/codesquad-members-2023/issue-tracker-max/assets/111998760/6689b9ad-c631-40d8-bda4-180f075abc19",
      })
    );
  }),

  rest.post("/api/issues/:issueId/assignees", async (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.post("/api/issues/:issueId/labels", async (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.post("/api/issues/:issueId/milestone", async (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.get("/api/issues/:issueId/comments", async (req, res, ctx) => {
    const cursor = req.url.searchParams.get("cursor");
    if (cursor === "0") {
      return res(ctx.status(200), ctx.json(comment0));
    }
    if (cursor === "1") {
      return res(ctx.status(200), ctx.json(comment1));
    }
    if (cursor === "2") {
      return res(ctx.status(200), ctx.json(comment2));
    }
  }),

  rest.post("/api/issues/:issueId/comments", async (req, res, ctx) => {
    const { content } = await req.json<{ content: string }>();

    return res(
      ctx.status(201),
      ctx.json({
        commentId: 1000,
        content: content,
        createdAt: new Date().toISOString(),
      })
    );
  }),

  rest.put("/api/issues/:issueId/comments/:commentId", async (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.put("/api/issues/:issueId/content", async (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.post("/api/labels", async (_, res, ctx) => {
    return res(ctx.status(201));
  }),

  rest.patch("/api/labels/:labelId", async (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.delete("/api/labels/:labelId", async (_, res, ctx) => {
    return res(ctx.status(204));
  }),

  rest.post("/api/milestones", async (_, res, ctx) => {
    return res(ctx.status(201));
  }),

  rest.put("/api/milestones/:milestoneId", async (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.put("/api/milestones/:milestoneId", async (_, res, ctx) => {
    // CONTENT
    return res(ctx.status(200));
  }),

  rest.put("/api/milestones/:milestoneId", async (req, res, ctx) => {
    const state = req.url.searchParams.get("state");
    if (state === "open" || state === "closed") {
      return res(ctx.status(200));
    }
  }),

  rest.delete("/api/milestones/:milestoneId", async (_, res, ctx) => {
    return res(ctx.status(204));
  }),
];
