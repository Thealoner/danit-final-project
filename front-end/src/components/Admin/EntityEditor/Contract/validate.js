import moment from 'moment';
import {
  isNumeric
} from '../../../../helpers/validation';

const validate = values => {
  let errors = {};
  let numericValues = [
    'credit',
    'price',
    'cost',
    'unitsNumber',
    'term',
    'freezeTimes',
    'freezeDays',
    'accessWithoutCardTimesLimit',
    'freezeMinTerm',
    'autoActivateAfterDays',
    'guestVisits',
    'usersMin',
    'visitTime'
  ];
  const required = [
    'title',
    'term',
    'price',
    'freezeTimes',
    'freezeDays',
    'freezeMinTerm',
    'accessWithoutCardTimesLimit',
    'autoActivateAfterDays',
    'guestVisits',
    'usersMin',
    'visitTime'
  ];

  required.forEach(field => {
    if (!values[field]) {
      errors[field] = 'Обязательное поле';
    }
  });

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

  numericValues.forEach((numericValue) => {
    if (values[numericValue] && !isNumeric(values[numericValue])) {
      errors[numericValue] = 'Некорректное значение';
    }
  });

  return errors;
};

export default validate;
