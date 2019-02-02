const validateAllRequired = values => {
  let errors = {};

  Object.keys(values).forEach(key => {
    if (!values[key]) {
      errors[key] = 'Обязательное поле';
    }
  });

  return errors;
};

export default validateAllRequired;