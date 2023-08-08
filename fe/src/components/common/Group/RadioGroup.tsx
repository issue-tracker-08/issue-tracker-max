import { RadioContext } from "context/radioContext";
import { ReactNode } from "react";
import { Fieldset } from "./Fieldset";

export default function RadioGroup({
  children,
  value,
  onChange,
}: {
  children: ReactNode;
  value: number;
  onChange: (value: number) => void;
}) {
  return (
    <Fieldset>
      <RadioContext.Provider value={{ value, onChange }}>
        {children}
      </RadioContext.Provider>
    </Fieldset>
  );
}
