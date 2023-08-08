const minuteInMs = 1000 * 60;
const hourInMs = minuteInMs * 60;
const dayInMs = hourInMs * 24;
const weekInMs = dayInMs * 7;
const monthInMs = weekInMs * 4;
const yearInMs = monthInMs * 12;

// 1 - 6 Days
// 1 - 3 weeks
// If over 1 month, `Month Date`
// If over 1 year, `Month Date, Year`
// Ex: `timestamp === "2023-07-27 10:42:40"`
export const convertPastTimestamp = (timestamp: string) => {
  const startDate = new Date(timestamp);
  if (isNaN(startDate.getTime())) {
    throw new Error("Invalid timestamp");
  }

  const currDate = new Date();

  const diffMs = currDate.getTime() - startDate.getTime();
  if (diffMs < 0) throw Error("timestamp cannot be in the future.");

  if (diffMs < minuteInMs) {
    return "방금 전";
  } else if (diffMs < hourInMs) {
    return `${Math.floor(diffMs / minuteInMs)}분 전`;
  } else if (diffMs < dayInMs) {
    return `${Math.floor(diffMs / hourInMs)}시간 전`;
  } else if (diffMs < weekInMs) {
    return `${Math.floor(diffMs / dayInMs)}일 전`;
  } else if (diffMs < monthInMs) {
    return `${Math.floor(diffMs / weekInMs)}주 전`;
  } else if (
    diffMs < yearInMs &&
    startDate.getFullYear() === currDate.getFullYear()
  ) {
    const month = startDate.getMonth();
    const date = startDate.getDate();
    return `${month + 1}/${date}`;
  } else {
    const year = startDate.getFullYear();
    const month = startDate.getMonth();
    const date = startDate.getDate();
    return `${year}/${month + 1}/${date}`;
  }
};
