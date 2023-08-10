import DropdownIndicator from "@components/Dropdown/DropdownIndicator";
import { DropdownItemType } from "@components/Dropdown/types";
import LabelTag from "@components/Label/LabelTag";
import { Label } from "@customTypes/index";
import useFetch from "@hooks/useFetch";
import { getLabels } from "api";
import styled from "styled-components";
import CheckboxGroup from "../Group/CheckboxGroup";
import { Container } from "./Container.style";

export default function LabelField({
  labels,
  onLabelChange,
  onEditLabels,
}: {
  labels: Set<number>;
  onLabelChange: (labels: Set<number>) => void;
  onEditLabels?: () => void;
}) {
  const { data: labelList } = useFetch(getLabels);

  const labelDropdownList: DropdownItemType[] | undefined = labelList?.map(
    (label) => ({
      id: label.labelId,
      variant: "withColor",
      name: "label",
      content: label.name,
      colorFill: label.backgroundColor,
    })
  );

  const generateLabels = (labelList: Label[]) => {
    const currentLabels = labelList.filter((label) =>
      labels.has(label.labelId)
    );
    return currentLabels.map(
      ({ labelId, name, backgroundColor, fontColor }) => {
        return (
          <LabelTag
            {...{
              key: labelId,
              name,
              backgroundColor,
              fontColor,
            }}
          />
        );
      }
    );
  };

  return (
    <Container>
      {labelDropdownList && (
        <CheckboxGroup values={labels} onChange={onLabelChange}>
          <DropdownIndicator
            displayName="레이블"
            dropdownPanelVariant="select"
            dropdownName="label"
            dropdownList={labelDropdownList}
            dropdownPanelPosition="right"
            dropdownOption="multiple"
            outsideClickHandler={onEditLabels}
          />
        </CheckboxGroup>
      )}
      {labelList && !!labels.size && (
        <Wrapper>{generateLabels(labelList)}</Wrapper>
      )}
    </Container>
  );
}

const Wrapper = styled.div`
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
`;
