import labelIcon from "@assets/icon/label.svg";
import milestoneIcon from "@assets/icon/milestone.svg";
import plusIcon from "@assets/icon/plus.svg";
import FilterBar from "@components/FilterBar";
import IssuesTableBody from "@components/Table/IssuesTable/IssuesTableBody";
import IssuesTableHeader from "@components/Table/IssuesTable/IssuesTableHeader";
import { Table } from "@components/Table/Table.style";
import Button from "@components/common/Button";
import TabBar from "@components/common/TabBar";
import useFetch from "@hooks/useFetch";
import { getIssues, getLabels, getMilestones } from "api";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";

export default function IssuesPage() {
  const navigate = useNavigate();
  const moveToNewIssuePage = () => navigate("/issues/new");

  const { data: issuesList } = useFetch(getIssues);
  const { data: labelsList } = useFetch(getLabels);
  const { data: milestonesList } = useFetch(getMilestones);

  const [selectedIssueIds, setSelectedIssueIds] = useState<Set<number>>(
    new Set()
  );

  const isAllIssuesSelected =
    selectedIssueIds.size === issuesList?.length && selectedIssueIds.size > 0;
  const numOpen = issuesList?.filter((issue) => issue.isOpen).length || 0;
  const numClosed = issuesList ? issuesList.length - numOpen : 0;

  const toggleSelectIssue = (id: number) => {
    setSelectedIssueIds((prev) => {
      const newSet = new Set(prev);
      if (prev.has(id)) {
        newSet.delete(id);
      } else {
        newSet.add(id);
      }
      return newSet;
    });
  };

  const selectAllIssues = () => {
    const allIssueIds = issuesList?.map((issue) => issue.issueNumber);
    setSelectedIssueIds(new Set(allIssueIds));
  };

  const deselectAllIssues = () => {
    setSelectedIssueIds(new Set<number>());
  };

  const toggleSelectAll = () => {
    if (isAllIssuesSelected) {
      deselectAllIssues();
    } else {
      selectAllIssues();
    }
  };

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

  return (
    <>
      <IssuesNavBar>
        <FilterBar />

        <div className="right-wrapper">
          <TabBar
            currentTabName=""
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

      <Table>
        <IssuesTableHeader
          {...{
            numOpen,
            numClosed,
            numSelectedIssues: selectedIssueIds.size,
            isAllIssuesSelected,
            toggleSelectAll,
          }}
        />
        <IssuesTableBody
          {...{ issuesList, selectedIssueIds, toggleSelectIssue }}
        />
      </Table>
    </>
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
