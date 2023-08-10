export enum DropdownNameKOR {
  assignee = "담당자",
  author = "작성자",
  label = "레이블",
  milestone = "마일스톤",
  issueState = "상태",
  issue = "이슈",
  fontColor = "텍스트 색상",
}

export type DropdownName = keyof typeof DropdownNameKOR;

export type DropdownPanelVariant = "filter" | "select" | "modify";

export type DropdownOption = "multiple" | "single";

export type DropdownPanelType = {
  variant: DropdownPanelVariant;
  dropdownName: DropdownName;
  dropdownList: DropdownItemType[];
  position: "left" | "right";
  onOutsideClick: (evt: MouseEvent) => void;
  dropdownOption: DropdownOption;
};

export type DropdownItemType =
  | {
      id: number;
      variant: "withImg";
      name: string;
      content: string;
      imgSrc: string;
    }
  | {
      id: number;
      variant: "withColor";
      name: string;
      content: string;
      colorFill: string;
    }
  | { id: number; variant: "plain"; name: string; content: string };
