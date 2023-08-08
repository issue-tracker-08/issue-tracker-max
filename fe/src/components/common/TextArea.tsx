import GripIcon from "@assets/icon/grip.svg";
import paperClipIcon from "@assets/icon/paperclip.svg";
import { postImage } from "api";
import { debounce } from "lodash";
import { useEffect, useState } from "react";
import styled from "styled-components";
import { Label } from "./Label";

export default function TextArea({
  name,
  value,
  appendContent,
  ...props
}: {
  value: string;
  appendContent: (content: string) => void;
} & React.TextareaHTMLAttributes<HTMLTextAreaElement>) {
  const [isCharCountShown, setIsCharCountShown] = useState(false);

  useEffect(() => {
    const DEBOUNCE_TIME = 500;
    const debouncedHandleCharCountShown = debounce(
      handleCharCountShown,
      DEBOUNCE_TIME
    );

    if (value) {
      debouncedHandleCharCountShown();
    }

    return () => debouncedHandleCharCountShown.cancel();
  }, [value]);

  const CHAR_SHOW_TIME = 2000;
  const charCountMessage = value && `띄어쓰기 포함 ${value.length}자`;

  const handleCharCountShown = () => {
    setIsCharCountShown(true);

    setTimeout(() => {
      setIsCharCountShown(false);
    }, CHAR_SHOW_TIME);
  };

  const onFileInputChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const files = e.target.files;
    if (!files) return;

    const file = files[0];
    const filename = file.name;

    const {
      data: { fileUrl },
    } = await postImage(file);
    if (fileUrl) {
      appendContent(`![${filename}](${fileUrl})`);
    }
  };

  return (
    <TextAreaContainer>
      <TextWrapper>
        {value && <Label htmlFor={name}>{props.placeholder}</Label>}
        <StyledTextArea id={name} value={value} {...props} />
        <div className="caption">
          {isCharCountShown && (
            <CharCountText>{charCountMessage}</CharCountText>
          )}
          <img src={GripIcon} alt="grip-icon" />
        </div>
      </TextWrapper>
      <FileUploadWrapper>
        <Label className="file-upload-label" htmlFor="file-upload-input">
          <img
            className="file-upload-icon"
            src={paperClipIcon}
            alt="파일 첨부 아이콘"
          />
          파일 첨부하기
          <input
            type="file"
            id="file-upload-input"
            onChange={onFileInputChange}
          />
        </Label>
      </FileUploadWrapper>
    </TextAreaContainer>
  );
}

const TextAreaContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: "center";
  color: ${({ theme: { neutral } }) => neutral.text.weak};
  border-radius: ${({ theme: { radius } }) => `${radius.l}`};
  background-color: ${({ theme: { neutral } }) => neutral.surface.bold};
  &:focus-within {
    border: ${({ theme: { neutral, border } }) =>
      `${border.default} ${neutral.border.defaultActive}`};
    background-color: ${({ theme: { neutral } }) => neutral.surface.strong};
  }
  &:disabled {
    opacity: ${({ theme: { opacity } }) => opacity.disabled};
  }
`;

const TextWrapper = styled.div`
  display: flex;
  flex-direction: column;
  padding: 16px;
  gap: 8px;
  border-bottom: ${({ theme: { border, neutral } }) =>
    `${border.dashed} ${neutral.border.default}`};

  .caption {
    display: flex;
    justify-content: flex-end;
    gap: 8px;
  }
`;

const StyledTextArea = styled.textarea`
  display: flex;
  color: ${({ theme: { neutral } }) => neutral.text.default};
  font: ${({ theme: { font } }) => font.displayMD16};
  caret-color: ${({ theme: { palette } }) => palette.blue};

  &::placeholder {
    color: ${({ theme: { neutral } }) => neutral.text.weak};
  }

  &:focus {
    color: ${({ theme: { neutral } }) => neutral.text.strong};
  }

  &::-webkit-scrollbar {
    width: 14px;
    height: 14px;
  }

  &::-webkit-scrollbar-thumb {
    outline: none;
    border-radius: 10px;
    border: 4px solid transparent;
    box-shadow: ${({ theme: { neutral } }) =>
      `inset 6px 6px 0 ${neutral.border.default}`};
  }

  &::-webkit-scrollbar-thumb:hover {
    border: 4px solid transparent;
    box-shadow: ${({ theme: { neutral } }) =>
      `inset 6px 6px 0 ${neutral.border.defaultActive}`};
  }

  &::-webkit-scrollbar-track {
    box-shadow: none;
    background-color: transparent;
  }
`;

const CharCountText = styled.span`
  display: flex;
  justify-content: flex-end;
  color: ${({ theme: { neutral } }) => neutral.text.weak};
  font: ${({ theme: { font } }) => font.displayMD12};
`;

const FileUploadWrapper = styled.div`
  width: 100%;
  height: 52px;
  display: flex;
  padding: 0 16px;

  .file-upload-label {
    width: 100%;
    gap: 4px;
  }

  #file-upload-input {
    display: none;
  }

  .file-upload-icon {
    width: 16px;
    height: 16px;
    filter: ${({ theme: { filter } }) => filter.neutralTextDefault};
  }

  :hover {
    cursor: pointer;
  }
`;
