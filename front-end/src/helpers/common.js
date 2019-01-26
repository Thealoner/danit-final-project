export const formatDateString = dateString => {
  const date = new Date(dateString);
  const month = ((date.getMonth().toString().length > 1) ? (date.getMonth() + 1) : ('0' + (date.getMonth() + 1)));
  const day = ((date.getDate().toString().length > 1) ? date.getDate() : ('0' + date.getDate()));
  const year = date.getFullYear();

  return day + '-' + month + '-' + year;
};