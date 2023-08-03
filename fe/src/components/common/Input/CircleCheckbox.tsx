import checkOffCircle from "@assets/icon/checkOffCircle.svg";
import checkOnCircle from "@assets/icon/checkOnCircle.svg";
import { styled } from "styled-components";

export default function CircleCheckbox(
  props: React.InputHTMLAttributes<HTMLInputElement>
) {
  return (
    <StyledCircleCheckbox className="input-checkbox">
      <input type="checkbox" {...props} />
      <img className="checkbox-img" src={checkOffCircle} alt="checkbox" />
    </StyledCircleCheckbox>
  );
}

const StyledCircleCheckbox = styled.label`
  width: 16px;
  height: 16px;
  cursor: pointer;

  input[type="checkbox"] {
    width: inherit;
    height: inherit;
    display: none;
  }

  input[type="checkbox"]:not(:checked) + .checkbox-img {
    content: url(${checkOffCircle});
  }

  input[type="checkbox"]:checked + .checkbox-img {
    content: url(${checkOnCircle});
  }

  .checkbox-img {
    filter: ${({ theme: { filter } }) => filter.neutralTextDefault};
  }
`;
