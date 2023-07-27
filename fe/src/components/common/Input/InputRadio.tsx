import checkOffCircle from "@assets/icon/checkOffCircle.svg";
import checkOnCircle from "@assets/icon/checkOnCircle.svg";
import { styled } from "styled-components";

export default function InputRadio(
  props: React.InputHTMLAttributes<HTMLInputElement>
) {
  return (
    <StyledInputRadio className="input-radio">
      <input type="radio" {...props} />
      <img className="radio-img" src={checkOffCircle} alt="Radio" />
    </StyledInputRadio>
  );
}

const StyledInputRadio = styled.label`
  width: 16px;
  height: 16px;
  cursor: pointer;

  input[type="radio"] {
    width: inherit;
    height: inherit;
    display: none;
  }

  input[type="radio"]:not(:checked) + .radio-img {
    content: url(${checkOffCircle});
  }

  input[type="radio"]:checked + .radio-img {
    content: url(${checkOnCircle});
  }

  .radio-img {
    filter: ${({ theme: { filter } }) => filter.neutralTextDefault};
  }
`;
