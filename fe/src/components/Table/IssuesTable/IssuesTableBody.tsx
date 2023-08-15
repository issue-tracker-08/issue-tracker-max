import { IssueItem as IssueItemType } from "@customTypes/index";
import { EmptyTableBodyItem, TableBody } from "../Table.style";
import IssuesTableItem from "./IssuesTableItem";

export default function IssuesTableBody({
  issuesList,
  selectedIssueIds,
  toggleSelectIssue,
}: {
  issuesList: IssueItemType[] | null;
  selectedIssueIds: Set<number>;
  toggleSelectIssue: (id: number) => void;
}) {
  return (
    <TableBody>
      {issuesList ? (
        <ul>
          {issuesList.map((issue) => (
            <IssuesTableItem
              {...{
                key: issue.issueNumber,
                issue,
                isSelected: selectedIssueIds.has(issue.issueNumber),
                toggleSelectIssue,
              }}
            />
          ))}
        </ul>
      ) : (
        <EmptyTableBodyItem>등록된 이슈가 없습니다.</EmptyTableBodyItem>
      )}
    </TableBody>
  );
}
