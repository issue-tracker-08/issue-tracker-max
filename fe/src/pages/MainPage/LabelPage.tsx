import labelIcon from "@assets/icon/label.svg";
import milestoneIcon from "@assets/icon/milestone.svg";
import plusIcon from "@assets/icon/plus.svg";
import { Table, TableBodyLabels, TableHeaderLabels } from "@components/Table";
import Button from "@components/common/Button";
import TabBar from "@components/common/TabBar";
import useFetch from "@hooks/useFetch";
import { getLabels, getMilestones } from "api";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";

export default function LabelPage() {
  const navigate = useNavigate();

  const { data: labelsList } = useFetch(getLabels);
  const { data: milestonesList } = useFetch(getMilestones);
  const [isAddNewLabel, setIsAddNewLabel] = useState(false);

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

  const openAddNewLabel = () => {
    setIsAddNewLabel(true);
  };

  return (
    <StyledLabelPage>
      <LabelNav>
        <TabBar
          left={tabBarLeftInfo}
          right={tabBarRightInfo}
          borderStyle="outline"
        />

        <Button
          variant="container"
          size="S"
          disabled={isAddNewLabel === true}
          onClick={openAddNewLabel}>
          <img src={plusIcon} alt="편집 취소" />
          <span>레이블 추가</span>
        </Button>
      </LabelNav>

      {isAddNewLabel && <div>add new label component</div>}

      <Table>
        <TableHeaderLabels numLabels={labelsList ? labelsList.length : 0} />
        <TableBodyLabels labelsList={labelsList} />
      </Table>
    </StyledLabelPage>
  );
}

const StyledLabelPage = styled.div`
  display: flex;
  flex-direction: column;
  gap: 24px;
`;

const LabelNav = styled.nav`
  display: flex;
  justify-content: space-between;
`;
