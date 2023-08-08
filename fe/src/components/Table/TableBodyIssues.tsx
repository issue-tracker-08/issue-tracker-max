import IssueItem from "@components/Issues/IssueItem";
import { IssueItem as IssueItemType } from "@customTypes/index";
import { styled } from "styled-components";
import TableBody from "./TableBody";

export default function TableBodyIssues({
  issuesList,
}: {
  issuesList: IssueItemType[];
}) {
  return (
    <TableBody>
      <TableBodyContents>
        {issuesList.length > 0 ? (
          <ul>
            {issuesList.map((issue) => (
              <IssueItem key={issue.issueNumber} issue={issue} />
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
