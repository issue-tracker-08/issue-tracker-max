import { TableBodyItemIssue } from "@components/Table/TableBodyItem/";
import { IssueItem as IssueItemType } from "@customTypes/index";
import EmptyTableBodyItem from "../TableBodyItem/EmptyTableBodyItem";
import TableBody from "./TableBody";

export default function TableBodyIssues({
  issuesList,
}: {
  issuesList: IssueItemType[] | null;
}) {
  return (
    <TableBody>
      {issuesList ? (
        <ul>
          {issuesList.map((issue) => (
            <TableBodyItemIssue key={issue.issueNumber} issue={issue} />
          ))}
        </ul>
      ) : (
        <EmptyTableBodyItem>등록된 이슈가 없습니다.</EmptyTableBodyItem>
      )}
    </TableBody>
  );
}
