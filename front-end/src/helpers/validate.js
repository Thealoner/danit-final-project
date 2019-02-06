// import moment from 'moment';

const validate = (values, {
  requiredFields,
  numericFields,
  dateFields,
  emailFields,
  passwordFields
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
        errors[numericValue] = 'Введите число';
      }
    });
  }

  if (dateFields) {
    dateFields.forEach(date => {
      if (values[date] && !isDate(values[date])) {
        errors[date] = 'Некорректная дата';
      }
    });
  }

  if (emailFields) {
    emailFields.forEach(email => {
      if (values[email] && !isEmail(values[email])) {
        errors[email] = 'Некорректный email';
      }
    });
  }

  if (passwordFields) {
    passwordFields.forEach(password => {
      if (values[password] && !isPassword(values[password])) {
        errors[password] = 'Пароль должен быть не менее 6 символов';
      }
    });
  }

  return errors;
};

const isNumeric = value => {
  return !isNaN(parseFloat(value)) && isFinite(value);
};

const isDate = value => {
  return true;
};

const isEmail = value => {
  return /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(value);
};

const isPassword = value => {
  return value.length >= 6; 
}

export default validate;
