import plusIcon from "@assets/icon/plus.svg";
import FilterBar from "@components/FilterBar";
import { Table, TableBodyIssues, TableHeaderIssues } from "@components/Table";
import Button from "@components/common/Button";
import TabBar from "@components/common/TabBar";
import { IssueItem, Label, Milestone } from "@customTypes/index";
import useFetch from "@hooks/useFetch";
import { getIssues, getLabels, getMilestones } from "api";
import { styled } from "styled-components";

export default function IssuesPage() {
  const issuesList = useFetch<IssueItem[]>([], getIssues);
  const labelsList = useFetch<Label[]>([], getLabels);
  const milestonesList = useFetch<Milestone[]>([], getMilestones);

  const numOpen = issuesList.filter((issue) => issue.isOpen).length;
  const numClosed = issuesList.length - numOpen;

  return (
    <div>
      <IssuesNavBar>
        <FilterBar />

        <div className="right-wrapper">
          <TabBar
            labelCount={labelsList.length}
            milestoneCount={milestonesList.length}
          />
          <Button size="S" variant="container">
            <img src={plusIcon} alt="이슈 작성" />
            이슈 작성
          </Button>
        </div>
      </IssuesNavBar>

      <Table>
        <TableHeaderIssues {...{ numOpen, numClosed }} />
        <TableBodyIssues issuesList={issuesList} />
      </Table>
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
