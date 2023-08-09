import { styled } from "styled-components";
import TableHeader from "./TableHeader";

export default function TableHeaderLabels({
  numLabels,
}: {
  numLabels: number;
}) {
  return (
    <TableHeader>
      <TableHeaderContents>{numLabels}개의 레이블</TableHeaderContents>
    </TableHeader>
  );
}

const TableHeaderContents = styled.div`
  font: ${({ theme: { font } }) => font.displayBold16};
  color: ${({ theme: { neutral } }) => neutral.text.default};
`;
