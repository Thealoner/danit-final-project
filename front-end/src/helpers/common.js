import moment from 'moment';

export const formatDate = date => moment(date).format('DD-MM-YYYY');

export const isNumeric = (value) => {
  return !isNaN(parseFloat(value)) && isFinite(value);
};
