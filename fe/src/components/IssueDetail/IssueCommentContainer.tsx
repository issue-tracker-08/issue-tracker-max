import plusIcon from "@assets/icon/plus.svg";
import Comment from "@components/Comment";
import Button from "@components/common/Button";
import TextAreaContainer from "@components/common/TextArea/TextAreaContainer";
import { IssueComment, IssueDetails } from "@customTypes/index";
import useFetch from "@hooks/useFetch";
import { getComments, postComment } from "api";
import { useAuth } from "context/authContext";
import React, { useCallback, useEffect, useState } from "react";
import styled from "styled-components";

export default function IssueCommentContainer({
  issueNumber,
  issueDetails,
  updateIssueDetails,
}: {
  issueNumber: number;
  issueDetails: IssueDetails;
  updateIssueDetails: () => void;
}) {
  const [cursor, setCursor] = useState<number>(0);
  const [allComments, setAllComments] = useState<IssueComment[]>([]);
  const [newComment, setNewComment] = useState<string>("");
  const { userInfo } = useAuth();

  const { data: issueComments } = useFetch<{
    data: IssueComment[];
    hasMore: boolean;
    nextCursor: number;
  }>(
    useCallback(() => getComments(issueNumber, cursor), [issueNumber, cursor])
  );

  const handleScroll = useCallback(() => {
    const scrollHeight = document.documentElement.scrollHeight;
    const scrollTop = window.scrollY;
    const clientHeight = window.innerHeight;
    const margin = 200;

    const isNearlyBottom = scrollHeight - (scrollTop + clientHeight) <= margin;
    isNearlyBottom &&
      issueComments?.hasMore &&
      setCursor(issueComments.nextCursor);
  }, [issueComments]);

  useEffect(() => {
    issueComments && setAllComments((prev) => [...prev, ...issueComments.data]);
  }, [issueComments]);

  useEffect(() => {
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, [handleScroll]);

  const isFilled = !!newComment;

  const { author, createdAt, content } = issueDetails;

  const onContentChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const targetValue = e.target.value;
    setNewComment(targetValue);
  };

  const appendContent = (content: string) => {
    setNewComment((prev) => `${prev} ${content}`);
  };

  const onCommentSubmit = async () => {
    try {
      const { status, data } = await postComment(issueNumber, {
        content: newComment,
      });
      if (status === 201) {
        setNewComment("");
        onCommentAdd({ ...data, ...userInfo });
      }
    } catch (error) {
      // TODO: error handling
      console.log(error);
    }
  };

  const onCommentAdd = (newComment: IssueComment) => {
    setAllComments((prev) => [...prev, newComment]);
    updateIssueDetails();
  };

  const onCommentEdit = (commentId: number, newContent: string) => {
    setAllComments((prev) => {
      return prev.map((comment) => {
        if (comment.commentId === commentId) {
          return { ...comment, content: newContent };
        }
        return comment;
      });
    });
  };

  const commentList = allComments.map((comment) => (
    <Comment
      key={comment.commentId}
      issueNumber={issueNumber}
      commentId={comment.commentId}
      author={{ username: comment.username, profileUrl: comment.profileUrl }}
      createdAt={comment.createdAt}
      content={comment.content}
      isIssueAuthor={comment.username === author.username}
      onCommentEdit={onCommentEdit}
    />
  ));

  return (
    <StyledIssueCommentContainer className="comments-container">
      <Comment
        {...{
          issueNumber,
          author,
          createdAt,
          content,
          isIssueAuthor: true,
          onUpdateContent: updateIssueDetails,
        }}
      />
      {commentList}
      <TextAreaContainer
        name="comment"
        placeholder="코멘트를 입력하세요"
        value={newComment}
        rows={5}
        onChange={onContentChange}
        appendContent={appendContent}
      />
      <Button
        variant="container"
        size="S"
        disabled={!isFilled}
        onClick={onCommentSubmit}>
        <img src={plusIcon} alt="" />
        <span>코멘트 작성</span>
      </Button>
    </StyledIssueCommentContainer>
  );
}

const StyledIssueCommentContainer = styled.div`
  width: 100%;
  max-width: 960px;
  display: flex;
  flex-direction: column;
  gap: 24px;
  flex-grow: 1;

  align-items: flex-end;
`;
