import plusIcon from "@assets/icon/plus.svg";
import FilterBar from "@components/FilterBar";
import { Table, TableBodyIssues, TableHeaderIssues } from "@components/Table";
import ContainerButton from "@components/common/Button/ContainerButton";
import TabBar from "@components/common/TabBar";
import { styled } from "styled-components";

export default function IssuesPage() {
  return (
    <div>
      <IssuesNavBar>
        <FilterBar />

        <div className="right-wrapper">
          <TabBar labelCount={3} milestoneCount={2} />
          <ContainerButton size="S" variant="container">
            <img src={plusIcon} alt="이슈 작성" />
            이슈 작성
          </ContainerButton>
        </div>
      </IssuesNavBar>

      <Table>
        <TableHeaderIssues />
        <TableBodyIssues issuesList={[{ title: "이슈 제목" }]} />
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
