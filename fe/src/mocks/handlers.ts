import { rest } from "msw";

const users: Record<string, string> = {};

type AuthRequestBody = {
  loginId: string;
  password: string;
};

export const handlers = [
  rest.post("/api/auth/signup", async (req, res, ctx) => {
    const { loginId, password } = await req.json<AuthRequestBody>();
    const isExist = users[loginId];
    users[loginId] = password;

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
    const { loginId, password } = await req.json<AuthRequestBody>();
    const isExist = users[loginId];
    const isCorrectPassword = users[loginId] === password;

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
          loginId: loginId,
          profileUrl: "https://avatars.githubusercontent.com/u/48426991?v=4",
        },
      })
    );
  }),

  rest.get("/api/issues", async (_req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json([
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
          authorName: "bruni",
          assignees: [
            {
              username: "bruni",
              profileUrl:
                "https://avatars.githubusercontent.com/u/79886384?v=4",
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
          authorName: "Kakamotobi",
          assignees: [
            {
              username: "Kakamotobi",
              profileUrl:
                "https://avatars.githubusercontent.com/u/79886384?v=4",
            },
            {
              username: "Zoey",
              profileUrl:
                "https://avatars.githubusercontent.com/u/111998760?v=4",
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
          authorName: "Zoey",
          assignees: [
            {
              username: "Kakamotobi",
              profileUrl:
                "https://avatars.githubusercontent.com/u/79886384?v=4",
            },
            {
              username: "Zoey",
              profileUrl:
                "https://avatars.githubusercontent.com/u/111998760?v=4",
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
          authorName: "Kakamotobi",
          assignees: [
            {
              username: "Kakamotobi",
              profileUrl:
                "https://avatars.githubusercontent.com/u/79886384?v=4",
            },
          ],
          createdAt: "2022-12-31 10:42:40",
        },
      ])
    );
  }),

  rest.get("/api/labels", async (_req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json([
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
        {
          name: "feat",
          fontColor: "#000",
          backgroundColor: "#FACA02",
        },
      ])
    );
  }),

  rest.get("/api/milestones", async (_req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json([
        {
          milestoneId: 1,
          milestoneName: "Sprint #1",
          openIssueCount: 1,
          closedIssueCount: 0,
        },
        {
          milestoneId: 2,
          milestoneName: "Sprint #2",
          openIssueCount: 2,
          closedIssueCount: 0,
        },
      ])
    );
  }),
];

// TODO: 만료된 토큰에 대한 응답 처리
