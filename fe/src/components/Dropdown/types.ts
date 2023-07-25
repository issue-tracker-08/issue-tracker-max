export enum DropdownNameKOR {
  assignee = "담당자",
  author = "작성자",
  label = "레이블",
  milestone = "마일스톤",
}

export type DropdownName = keyof typeof DropdownNameKOR;

export type DropdownPanelType = {
  variant: "filter" | "select";
  dropdownName: DropdownName;
  dropdownList: DropdownItemType[];
};

export type DropdownItemType =
  | {
      variant: "withImg";
      name: string;
      content: string;
      imgSrc: string;
    }
  | { variant: "withColor"; name: string; content: string; colorFill: string }
  | { variant: "plain"; name: string; content: string };
