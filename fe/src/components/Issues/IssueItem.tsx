import alertIcon from "@assets/icon/alertCircle.svg";
import milestoneIcon from "@assets/icon/milestone.svg";
import InputCheckbox from "@components/common/Input/InputCheckbox";
import { styled } from "styled-components";

export default function IssueItem({ issue }: { issue: { title: string } }) {
  return (
    <StyledIssueItem>
      <div className="left-wrapper">
        <IssueHeader>
          <InputCheckbox />
          <div className="header-inner-wrapper">
            <img className="alert-icon" src={alertIcon} alt="열린 이슈" />
            <h4>{issue.title}</h4>
            {/* labels.map((label) => <LabelTag />) */}
          </div>
        </IssueHeader>

        <IssueDetails>
          <span>#이슈번호</span>
          <span>작성자 및 타임스탬프 정보</span>
          <span>
            <img src={milestoneIcon} alt="마일스톤" />
            마일스톤
          </span>
        </IssueDetails>
      </div>

      <div className="right-wrapper">
        <Avatar
          src="https://avatars.githubusercontent.com/u/79886384?v=4"
          alt="Assignee"
        />
      </div>
    </StyledIssueItem>
  );
}

const StyledIssueItem = styled.li`
  width: 100%;
  padding: 16px 32px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  .left-wrapper {
    display: flex;
    flex-direction: column;
    gap: 8px;
  }

  .right-wrapper {
    padding-right: 22px;
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

    h4 {
      font: ${({ theme: { font } }) => font.availableMD20};
    }
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
    img {
      margin-right: 8px;
    }
  }
`;

const Avatar = styled.img`
  width: 20px;
  height: 20px;
  border-radius: ${({ theme: { radius } }) => radius.half};
  overflow: hidden;
`;
