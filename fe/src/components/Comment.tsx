import editIcon from "@assets/icon/edit.svg";
import smileIcon from "@assets/icon/smile.svg";
import { styled } from "styled-components";
import Button from "./common/Button";

export default function Comment({
  username,
  profileUrl,
  createdAt,
  content,
  isIssueAuthor,
}: {
  username: string;
  profileUrl: string;
  createdAt: string;
  content: string;
  isIssueAuthor: boolean;
}) {
  return (
    <StyledComment>
      <Header>
        <div className="left-wrapper">
          {/* TODO: replace `img` with `Avatar` */}
          <img src={profileUrl} alt="" />
          <span className="comment-author">{username}</span>
          <span className="comment-date">{createdAt}</span>
        </div>

        <div className="right-wrapper">
          {isIssueAuthor && <AuthorTag>작성자</AuthorTag>}

          <Button variant="ghost" size="S">
            <img src={editIcon} alt="코멘트 편집" />
            <span>편집</span>
          </Button>

          <Button variant="ghost" size="S">
            <img src={smileIcon} alt="코멘트 반응" />
            <span>반응</span>
          </Button>
        </div>
      </Header>

      <Body>{content}</Body>
    </StyledComment>
  );
}

const StyledComment = styled.div`
  width: 100%;
  border: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  border-radius: ${({ theme: { radius } }) => radius.l};
`;

const Header = styled.header`
  width: 100%;
  height: 64px;
  padding: 16px 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};

  .left-wrapper {
    display: flex;
    gap: 8px;

    .comment-author {
      font: ${({ theme: { font } }) => font.displayMD16};
      color: ${({ theme: { neutral } }) => neutral.text.default};
    }

    .comment-date {
      font: ${({ theme: { font } }) => font.displayMD16};
      color: ${({ theme: { neutral } }) => neutral.text.weak};
    }
  }

  .right-wrapper {
    display: flex;
    gap: 16px;
  }
`;

const AuthorTag = styled.span`
  height: 24px;
  padding-inline: 12px;
  background-color: ${({ theme: { neutral } }) => neutral.surface.bold};
  font: ${({ theme: { font } }) => font.displayMD12};
  color: ${({ theme: { neutral } }) => neutral.text.weak};
  border: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  border-radius: ${({ theme: { radius } }) => radius.l};
  line-height: 24px;
`;

const Body = styled.div`
  width: 100%;
  padding: 16px 24px;
  font: ${({ theme: { font } }) => font.displayMD16};
  color: ${({ theme: { neutral } }) => neutral.text.default};
`;
