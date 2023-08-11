import alertIcon from "@assets/icon/alertCircle.svg";
import archiveIcon from "@assets/icon/archive.svg";
import editIcon from "@assets/icon/edit.svg";
import xIcon from "@assets/icon/xSquare.svg";
import Button from "@components/common/Button";
import TextInput from "@components/common/TextInput";
import { IssueDetails } from "@customTypes/index";
import { convertPastTimestamp } from "@utils/time";
import { putIssueIsOpen, putIssueTitle } from "api";
import { FormEvent, useState } from "react";
import { styled } from "styled-components";

export default function IssueDetailHeader({
  issueDetails,
  updateIssueTitle,
  updateIssueIsOpen,
}: {
  issueDetails: IssueDetails | null;
  updateIssueTitle: (newTitle: string) => void;
  updateIssueIsOpen: () => void;
}) {
  const { issueId, author, title, createdAt, isOpen, commentCount } =
    issueDetails || {
      issueId: 0,
      author: { username: "", profileURl: "" },
      title: "",
      createdAt: new Date().toISOString(),
      isOpen: true,
      commentCount: 0,
    };

  const [isEditTitle, setIsEditTitle] = useState(false);
  const [newTitle, setNewTitle] = useState(title);
  const [prevTitle, setPrevTitle] = useState(title);

  if (title !== prevTitle) {
    setPrevTitle(title);
    setNewTitle(title);
  }

  const onNewTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNewTitle(e.target.value);
  };

  const onNewTitleSubmit = async (e: FormEvent) => {
    e.preventDefault();

    try {
      const res = await putIssueTitle(issueId, {
        title: newTitle,
      });

      if (res.status === 200) {
        updateIssueTitle(newTitle);
        setIsEditTitle(false);
      }
    } catch (error) {
      // TODO
      console.error(error);
    }
  };

  const onUpdateIsOpen = async () => {
    try {
      const res = await putIssueIsOpen(issueId, {
        isOpen: !isOpen,
      });

      if (res.status === 200) {
        updateIssueIsOpen();
      }
    } catch (error) {
      // TODO
      console.error(error);
    }
  };

  return (
    <Header>
      <TitleAndButtons>
        {isEditTitle ? (
          <form action="" onSubmit={onNewTitleSubmit}>
            <TextInput
              name="제목"
              variant="short"
              placeholder="제목"
              value={newTitle}
              onChange={onNewTitleChange}
            />

            <ButtonsWrapper>
              <Button
                variant="outline"
                size="S"
                onClick={() => setIsEditTitle(false)}>
                <img src={xIcon} alt="편집 취소" />
                <span>편집 취소</span>
              </Button>
              <Button variant="container" size="S" disabled={title === ""}>
                <img src={editIcon} alt="편집 완료" />
                <span>편집 완료</span>
              </Button>
            </ButtonsWrapper>
          </form>
        ) : (
          <>
            <TitleWrapper>
              <h1>{title}</h1>
              <span>#{issueId}</span>
            </TitleWrapper>

            <ButtonsWrapper>
              <Button
                variant="outline"
                size="S"
                onClick={() => setIsEditTitle(true)}>
                <img src={editIcon} alt="제목 편집" />
                <span>제목 편집</span>
              </Button>
              <Button variant="outline" size="S" onClick={onUpdateIsOpen}>
                <img
                  src={isOpen ? archiveIcon : alertIcon}
                  alt={`이슈 ${isOpen ? "닫기" : "열기"}`}
                />
                <span>이슈 {`${isOpen ? "닫기" : "열기"}`}</span>
              </Button>
            </ButtonsWrapper>
          </>
        )}
      </TitleAndButtons>

      <IssueInfo>
        <IssueStateTag $isOpen={isOpen}>
          <img src={isOpen ? alertIcon : archiveIcon} alt="열린 이슈" />
          <span>{`${isOpen ? "열린" : "닫힌"}`} 이슈</span>
        </IssueStateTag>

        <p>
          이 이슈는 {convertPastTimestamp(createdAt)}에 {author?.username}에
          의해 열렸습니다 ∙ 코멘트 {commentCount}개
        </p>
      </IssueInfo>
    </Header>
  );
}

const Header = styled.header`
  width: 100%;
  margin-bottom: 24px;
`;

const TitleAndButtons = styled.div`
  width: 100%;
  margin-bottom: 16px;
  display: flex;
  justify-content: space-between;
  gap: 16px;

  form {
    width: 100%;
    display: flex;
    justify-content: space-between;
    gap: 16px;
  }
`;

const TitleWrapper = styled.div`
  display: flex;
  gap: 8px;

  h1 {
    font: ${({ theme: { font } }) => font.displayBold32};
    color: ${({ theme: { neutral } }) => neutral.text.strong};
  }

  span {
    font: ${({ theme: { font } }) => font.displayBold32};
    color: ${({ theme: { neutral } }) => neutral.text.weak};
  }
`;

const ButtonsWrapper = styled.div`
  display: flex;
  gap: 16px;
`;

const IssueInfo = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;

  p {
    font: ${({ theme: { font } }) => font.displayMD16};
    color: ${({ theme: { neutral } }) => neutral.text.weak};
  }
`;

const IssueStateTag = styled.div<{ $isOpen: boolean }>`
  height: 32px;
  padding-inline: 16px;
  display: flex;
  align-items: center;
  gap: 4px;
  background-color: ${({ theme: { palette, neutral }, $isOpen }) =>
    $isOpen ? palette.blue : neutral.text.weak};
  border-radius: ${({ theme: { radius } }) => radius.l};

  img {
    filter: ${({ theme: { filter } }) => filter.brandTextDefault};
  }

  span {
    font: ${({ theme: { font } }) => font.displayMD12};
    color: ${({ theme: { brand } }) => brand.text.default};
  }
`;
