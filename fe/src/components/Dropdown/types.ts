export enum DropdownNameKOR {
  assignee = "담당자",
  author = "작성자",
  label = "레이블",
  milestone = "마일스톤",
}

export type DropdownName = keyof typeof DropdownNameKOR;

export type DropdownItemType =
  | {
      type: "withImg";
      name: string;
      content: string;
      imgSrc: string;
    }
  | { type: "withColor"; name: string; content: string; colorFill: string }
  | { type: "onlyContent"; name: string; content: string };
