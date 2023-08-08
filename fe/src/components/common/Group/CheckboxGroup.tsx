import { CheckboxContext, checkFn, toggleFn } from "context/checkboxContext";
import { Fieldset } from "./Fieldset";

export default function CheckboxGroup({
  children,
  values,
  onChange,
}: {
  children: React.ReactNode;
  values: Set<number>;
  onChange: (values: Set<number>) => void;
}) {
  const isChecked: checkFn = (value) => values.has(value);
  const toggleCheck: toggleFn = ({ checked, value }) => {
    if (checked) {
      onChange(values.add(value));
    } else {
      values.delete(value);
      onChange(values);
    }
  };

  return (
    <Fieldset>
      <CheckboxContext.Provider value={{ isChecked, toggleCheck }}>
        {children}
      </CheckboxContext.Provider>
    </Fieldset>
  );
}
