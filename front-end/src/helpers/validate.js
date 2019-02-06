// import moment from 'moment';

const validate = (values, {
  requiredFields,
  numericFields,
  dateFields,
  emailFields
}) => {
  let errors = {};

  if (requiredFields) {
    requiredFields.forEach(field => {
      if (!values[field]) {
        errors[field] = 'Обязательное поле';
      }
    });
  }

  if (numericFields) {
    numericFields.forEach((numericValue) => {
      if (values[numericValue] && !isNumeric(values[numericValue])) {
        errors[numericValue] = 'Некорректное значение';
      }
    });
  }

  if (dateFields) {
    
  }

  return errors;
};

const isNumeric = (value) => {
  return !isNaN(parseFloat(value)) && isFinite(value);
};

// if (values.email && !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(values.email)) {
//   errors.email = 'Некорректный email адрес';
// }

// if (values.birthDate && (parseInt(moment(values.birthDate).fromNow()) > 110 || isNaN(parseInt(moment(values.birthDate).fromNow())))) {
//   errors.birthDate = 'Некорректный возраст';
// }

// if (values.phoneNumber && !/^(\s*)?(\+)?([- _():=+]?\d[- _():=+]?){10,14}(\s*)?$/.test(values.phoneNumber)) {
//   errors.phoneNumber = 'Некорректный номер телефона';
// }

// if (values.code && !/^\d{12}$/.test(values.code)) {
//   errors.code = 'Некорректный номер карты';
// }

export default validate;
