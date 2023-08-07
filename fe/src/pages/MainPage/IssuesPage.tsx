import labelIcon from "@assets/icon/label.svg";
import milestoneIcon from "@assets/icon/milestone.svg";
import plusIcon from "@assets/icon/plus.svg";
import FilterBar from "@components/FilterBar";
import { Table, TableBodyIssues, TableHeaderIssues } from "@components/Table";
import Button from "@components/common/Button";
import TabBar from "@components/common/TabBar";
import useFetch from "@hooks/useFetch";
import { getIssues, getLabels, getMilestones } from "api";
import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";

export default function IssuesPage() {
  const navigate = useNavigate();

  const { data: issuesList } = useFetch(getIssues);
  const { data: labelsList } = useFetch(getLabels);
  const { data: milestonesList } = useFetch(getMilestones);

  const numOpen = issuesList?.filter((issue) => issue.isOpen).length || 0;
  const numClosed = issuesList ? issuesList.length - numOpen : 0;

  const tabBarLeftInfo = {
    name: "레이블",
    count: labelsList ? labelsList.length : 0,
    iconSrc: labelIcon,
    callback: () => navigate("/labels"),
  };
  const tabBarRightInfo = {
    name: "마일스톤",
    count: milestonesList ? milestonesList.length : 0,
    iconSrc: milestoneIcon,
    callback: () => navigate("/milestones"),
  };

  const moveToNewIssuePage = () => navigate("/issues/new");

  return (
    <div>
      <IssuesNavBar>
        <FilterBar />

        <div className="right-wrapper">
          <TabBar
            left={tabBarLeftInfo}
            right={tabBarRightInfo}
            borderStyle="outline"
          />
          <Button size="S" variant="container" onClick={moveToNewIssuePage}>
            <img src={plusIcon} alt="이슈 작성" />
            이슈 작성
          </Button>
        </div>
      </IssuesNavBar>

      {issuesList && (
        <Table>
          <TableHeaderIssues {...{ numOpen, numClosed }} />
          <TableBodyIssues issuesList={issuesList} />
        </Table>
      )}
    </div>
  );
}

const IssuesNavBar = styled.div`
  width: 100%;
  margin-bottom: 24px;
  display: flex;
  justify-content: space-between;

  .right-wrapper {
    display: flex;
    align-items: center;
    gap: 16px;
  }
`;
