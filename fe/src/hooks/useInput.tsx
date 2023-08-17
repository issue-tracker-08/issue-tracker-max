import { useRef, useState } from "react";

type useInputOptions = {
  initialValue: string;
  minLength?: number;
  maxLength?: number;
};

export default function useInput({
  initialValue,
  maxLength,
  minLength = 0,
}: useInputOptions) {
  const [value, setValue] = useState(initialValue);
  const isValid = useRef(true);

  const validate = (receivedValue: string) => {
    isValid.current = receivedValue.length >= minLength;
    setValue(receivedValue);
  };

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const targetValue = e.target.value.trim();
    validate(targetValue);
  };

  return {
    value,
    onChange,
    minLength,
    maxLength,
    isValid: isValid.current,
  };
}
