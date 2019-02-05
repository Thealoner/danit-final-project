import moment from 'moment';

const isNumeric = (value) => {
  return !isNaN(parseFloat(value)) && isFinite(value);
};

const validateAllRequired = values => {
  let errors = {};
  let numericValues = ['credit', 'price', 'cost', 'unitsNumber', 'term', 'freezeTimes', 'freezeDays', 'accessWithoutCardTimesLimit', 'freezeMinTerm', 'autoActivateAfterDays', 'guestVisits', 'usersMin', 'visitTime'];

  Object.keys(values).forEach(key => {
    if (!values[key]) {
      errors[key] = 'Обязательное поле';
    }
  });

  if (values.email && !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(values.email)) {
    errors.email = 'Некорректный email адресс';
  }

  if (values.birthDate && (parseInt(moment(values.birthDate).fromNow()) > 110 || isNaN(parseInt(moment(values.birthDate).fromNow())))) {
    errors.birthDate = 'Некоректный возраст';
  }

  if (values.phoneNumber && !/^(\s*)?(\+)?([- _():=+]?\d[- _():=+]?){10,14}(\s*)?$/.test(values.phoneNumber)) {
    errors.phoneNumber = 'Некорректный номер телефона';
  }

  if (values.code && !/^\d{12}$/.test(values.code)) {
    errors.code = 'Некорректный номер карты';
  }

  numericValues.forEach((numericValue) => {
    if (values[numericValue] && !isNumeric(values[numericValue])) {
      errors[numericValue] = 'Некорректное значение';
    }
  });

  return errors;
};

export default validateAllRequired;
