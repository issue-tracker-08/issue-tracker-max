import alertIcon from "@assets/icon/alertCircle.svg";
import archiveIcon from "@assets/icon/archive.svg";
import milestoneIcon from "@assets/icon/milestone.svg";
import LabelTag from "@components/Label/LabelTag";
import InputCheckbox from "@components/common/Input/InputCheckbox";
import { IssueItem as IssueItemType } from "@customTypes/index";
import { convertPastTimestamp } from "@utils/time";
import { Link } from "react-router-dom";
import { styled } from "styled-components";

export default function IssuesTableItem({
  issue,
  isSelected,
  toggleSelectIssue,
}: {
  issue: IssueItemType;
  isSelected: boolean;
  toggleSelectIssue: (id: number) => void;
}) {
  const {
    issueNumber,
    isOpen,
    title,
    labels,
    milestone,
    author,
    assignees,
    createdAt,
  } = issue;

  // console.log(isSelected);

  return (
    <StyledIssueItem>
      <div className="left-wrapper">
        <IssueHeader>
          {/* TODO:  */}
          <InputCheckbox
            checked={isSelected}
            onChange={() => {
              toggleSelectIssue(issueNumber);
            }}
          />
          <div className="header-inner-wrapper">
            {isOpen ? (
              <img className="alert-icon" src={alertIcon} alt="열린 이슈" />
            ) : (
              <img className="alert-icon" src={archiveIcon} alt="닫힌 이슈" />
            )}
            <Link to={`/issues/${issueNumber}`}>
              <IssueTitle>{title}</IssueTitle>
            </Link>
            {labels.map(({ name, fontColor, backgroundColor }) => (
              <LabelTag {...{ key: name, name, fontColor, backgroundColor }} />
            ))}
          </div>
        </IssueHeader>

        <IssueDetails>
          <span>#{issueNumber}</span>
          <span>
            이 이슈는 {convertPastTimestamp(createdAt)}, {author.username}님에
            의해 작성되었습니다
          </span>
          {milestone && (
            <span>
              <img
                className="milestone-icon"
                src={milestoneIcon}
                alt="마일스톤"
              />
              {milestone}
            </span>
          )}
        </IssueDetails>
      </div>

      <div className="right-wrapper">
        <div className="assignees-wrapper">
          {assignees.map(({ username, profileUrl }, index) => (
            <Avatar
              key={username}
              className="avatar"
              src={profileUrl}
              alt={username}
              $index={index}
            />
          ))}
        </div>
      </div>
    </StyledIssueItem>
  );
}

const StyledIssueItem = styled.li`
  width: 100%;
  height: inherit;
  padding: 16px 32px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  &:not(:last-child) {
    border-bottom: ${({ theme: { border, neutral } }) =>
      `${border.default} ${neutral.border.default}`};
  }

  .left-wrapper {
    display: flex;
    flex-direction: column;
    gap: 8px;
  }

  .right-wrapper {
    padding-right: 22px;

    .assignees-wrapper {
      height: 20px;
      position: relative;
    }
  }
`;

const IssueHeader = styled.div`
  height: 32px;
  display: flex;
  align-items: center;

  .input-checkbox {
    margin-right: 32px;
  }

  .header-inner-wrapper {
    display: flex;
    gap: 8px;

    .alert-icon {
      filter: ${({ theme: { filter } }) => filter.brandTextWeak};
    }
  }
`;

const IssueTitle = styled.span`
  color: ${({ theme: { neutral } }) => neutral.text.strong};
  font: ${({ theme: { font } }) => font.availableMD20};

  &:hover {
    opacity: ${({ theme: { opacity } }) => opacity.hover};
  }
`;

const IssueDetails = styled.div`
  height: 24px;
  padding-left: 42px;
  display: flex;
  gap: 16px;
  color: ${({ theme: { neutral } }) => neutral.text.weak};
  font: ${({ theme: { font } }) => font.availableMD16};

  span {
    .milestone-icon {
      margin-right: 8px;
      filter: ${({ theme: { filter } }) => filter.neutralTextDefault};
    }
  }
`;

const Avatar = styled.img<{ $index: number }>`
  width: 20px;
  height: 20px;
  position: absolute;
  right: ${({ $index }) => $index * 10}px;
  border-radius: ${({ theme: { radius } }) => radius.half};
  overflow: hidden;
`;
