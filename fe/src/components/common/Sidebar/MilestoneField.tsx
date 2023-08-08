import DropdownIndicator from "@components/Dropdown/DropdownIndicator";
import { DropdownItemType } from "@components/Dropdown/types";
import { Milestone } from "@customTypes/index";
import useFetch from "@hooks/useFetch";
import { getMilestones } from "api";
import RadioGroup from "../Group/RadioGroup";
import ProgressBar from "../ProgressBar";
import { Container } from "./Container";

export default function MilestoneField({
  milestone: milestoneId,
  onMilestoneChange,
  onEditMilestone,
}: {
  milestone: number;
  onMilestoneChange: (milestoneId: number) => void;
  onEditMilestone?: () => void;
}) {
  const [milestonesList] = useFetch<Milestone[]>([], getMilestones);

  const milestoneDropdownList: DropdownItemType[] = milestonesList.map(
    (milestone) => ({
      id: milestone.milestoneId,
      variant: "plain",
      name: "milestone",
      content: milestone.milestoneName,
    })
  );

  const generateMilestone = () => {
    const milestone = milestonesList.find(
      (milestone) => milestone.milestoneId === milestoneId
    );

    return (
      milestone && (
        <ProgressBar
          name={milestone.milestoneName}
          openCount={milestone.openIssueCount}
          closeCount={milestone.closedIssueCount}
        />
      )
    );
  };

  return (
    <Container>
      <RadioGroup value={milestoneId} onChange={onMilestoneChange}>
        <DropdownIndicator
          displayName="마일스톤"
          dropdownPanelVariant="select"
          dropdownName="milestone"
          dropdownList={milestoneDropdownList}
          dropdownPanelPosition="right"
          outsideClickHandler={onEditMilestone}
        />
      </RadioGroup>
      {!!milestoneId && generateMilestone()}
    </Container>
  );
}
