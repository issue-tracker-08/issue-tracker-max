import IssueItem from "@components/Issues/IssueItem";
import { styled } from "styled-components";
import TableBody from "./TableBody";

export default function TableBodyIssues({
  issuesList,
}: {
  issuesList: { title: string }[];
}) {
  return (
    <TableBody>
      <TableBodyContents>
        {issuesList.length > 0 ? (
          <ul>
            {issuesList.map((issue) => (
              <IssueItem key={issue.title} issue={issue} /> // TODO: issue object & props
            ))}
          </ul>
        ) : (
          <EmptyItem>등록된 이슈가 없습니다.</EmptyItem>
        )}
      </TableBodyContents>
    </TableBody>
  );
}

const TableBodyContents = styled.div`
  width: 100%;
`;

const EmptyItem = styled.div`
  width: 100%;
  height: 96px;
  color: ${({ theme: { neutral } }) => neutral.text.weak};
  font: ${({ theme: { font } }) => font.displayMD16};
  line-height: 96px;
  text-align: center;
`;
