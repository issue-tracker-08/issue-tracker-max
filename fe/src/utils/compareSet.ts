export const compareSet = (
  prev: Set<number>,
  current: Set<number>
): { addUserAccountId: number[]; removeUserAccountId: number[] } => {
  const addedElements = [...current].filter((element) => !prev.has(element));
  const removedElements = [...prev].filter((element) => !current.has(element));

  const result = {
    addUserAccountId: addedElements,
    removeUserAccountId: removedElements,
  };

  return result;
};
