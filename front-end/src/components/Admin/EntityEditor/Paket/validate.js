import moment from 'moment';
import {
  isNumeric
} from '../../../../helpers/validation';

const validate = (values, {
  requiredFields,
  numericFields
}) => {
  debugger;
  let errors = {};
  

  requiredFields.forEach(field => {
    if (!values[field]) {
      errors[field] = 'Обязательное поле';
    }
  });

  numericFields.forEach((numericValue) => {
    if (values[numericValue] && !isNumeric(values[numericValue])) {
      errors[numericValue] = 'Некорректное значение';
    }
  });

  return errors;
};

export default validate;
