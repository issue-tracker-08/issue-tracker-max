import checkboxActiveIcon from "@assets/icon/checkBoxActive.svg";
import checkboxInitialIcon from "@assets/icon/checkBoxInitial.svg";
import { styled } from "styled-components";

export default function InputCheckbox(
  props: React.InputHTMLAttributes<HTMLInputElement>
) {
  return (
    <StyledInputCheckbox className="input-checkbox">
      <input type="checkbox" {...props} />
      <img className="checkbox-img" src={checkboxInitialIcon} alt="Checkbox" />
    </StyledInputCheckbox>
  );
}

const StyledInputCheckbox = styled.label`
  width: 16px;
  height: 16px;
  cursor: pointer;

  input[type="checkbox"] {
    width: inherit;
    height: inherit;
    display: none;
  }

  input[type="checkbox"]:not(:checked) + .checkbox-img {
    content: url(${checkboxInitialIcon});
  }

  input[type="checkbox"]:checked + .checkbox-img {
    content: url(${checkboxActiveIcon});
  }
`;
