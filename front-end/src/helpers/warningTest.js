const warningTest = values => {
  let warnings = {};

  if (values.unitsNumber < 60) {
    warnings.unitsNumber = 'Осталось очень мало единиц товара';
  }

  return warnings;
};

export default warningTest;