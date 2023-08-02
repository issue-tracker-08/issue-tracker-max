import XIcon from "@assets/icon/xSquare.svg";
import { Avatar } from "@components/common/Avatar";
import Button from "@components/common/Button";
import Sidebar from "@components/common/Sidebar/Sidebar";
import TextArea from "@components/common/TextArea";
import TextInput from "@components/common/TextInput";
import { postIssue } from "api";
import { useAuth } from "context/authContext";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

export default function NewIssuePage() {
  const [title, setTitle] = useState<string>("");
  const [content, setContent] = useState<string>("");
  const [assignees, setAssignees] = useState<number[]>([]);
  const [labels, setLabels] = useState<number[]>([]);

  const { userInfo } = useAuth();

  const navigate = useNavigate();
  const moveMainPage = () => navigate("/issues");
  const moveIssueDetailPage = (issueId: number) =>
    navigate(`/issues/${issueId}`);

  const isFilled = !!title;

  const onAssigneeChange = (assignees: number[]) => {
    setAssignees(assignees);
  };

  const onLabelChange = (labels: number[]) => {
    setLabels(labels);
  };

  const onTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const targetValue = e.target.value;
    setTitle(targetValue);
  };

  const onContentChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const targetValue = e.target.value;
    setContent(targetValue);
  };

  const appendContent = (content: string) => {
    setContent((prev) => `${prev} ${content}`);
  };

  const onIssueSubmit = async () => {
    const {
      data: { issueId },
    } = await postIssue({
      title,
      content,
      assignees,
      labels,
    });
    if (issueId) {
      moveIssueDetailPage(issueId);
    }
  };

  return (
    <StyledNewIssuePage>
      <Title>새로운 이슈 작성</Title>
      <div className="wrapper">
        <Avatar
          src={userInfo.profileUrl}
          alt={`${userInfo.username}-avatar`}
          $size="M"
        />
        <div className="form">
          <TextInput
            name="title"
            variant="tall"
            placeholder="제목"
            value={title}
            hasError={!isFilled}
            helpText="이슈 제목은 필수로 입력해주세요!"
            onChange={onTitleChange}
          />
          <TextArea
            name="comment"
            placeholder="코멘트를 입력하세요"
            value={content}
            rows={30}
            onChange={onContentChange}
            appendContent={appendContent}
          />
        </div>
        <div>
          <Sidebar
            {...{ assignees, labels, onAssigneeChange, onLabelChange }}
          />
        </div>
      </div>

      <footer className="footer">
        <Button variant="ghost" size="M" onClick={moveMainPage}>
          <img src={XIcon} alt="" />
          <span>작성 취소</span>
        </Button>
        <Button
          variant="container"
          size="L"
          onClick={onIssueSubmit}
          disabled={!isFilled}>
          완료
        </Button>
      </footer>
    </StyledNewIssuePage>
  );
}

const StyledNewIssuePage = styled.div`
  display: flex;
  width: 100%;
  flex-direction: column;
  gap: 24px;

  .wrapper {
    display: flex;
    width: 100%;
    gap: 24px;
    padding: 24px 0;
    border-bottom: ${({ theme: { border, neutral } }) =>
      `${border.default} ${neutral.border.default}`};
    border-top: ${({ theme: { border, neutral } }) =>
      `${border.default} ${neutral.border.default}`};
  }

  .form {
    display: flex;
    flex-grow: 1;
    flex-direction: column;
    gap: 8px;
  }

  .footer {
    display: flex;
    justify-content: flex-end;
    gap: 32px;
  }
`;

const Title = styled.h1`
  font: ${({ theme: { font } }) => font.displayBold32};
  color: ${({ theme: { neutral } }) => neutral.text.strong};
`;
