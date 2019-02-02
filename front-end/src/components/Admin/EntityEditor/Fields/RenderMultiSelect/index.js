import React from 'react';
import { Dropdown } from 'semantic-ui-react';

const RenderMultiSelect = ({
  input,
  label,
  type,
  name,
  options,
  changeField,
  meta: { touched, error, warning }
}) => {
  // debugger;
  // const values = (input.value && input.value[0]) ? input.value.map(el => ''+el.id) : [];

  return (
    <div className="form-group field field-string">
      <label className="control-label">{label}</label>
      <div>
        <Dropdown value={input.value} fluid multiple selection options={options} onChange={changeField} className={error ? 'field_error-input' : null || warning ? 'field_warning-input' : null} />
        {touched &&
          ((error && <div className="field_error">{error}</div>) ||
            (warning && <div className="field_warning">{warning}</div>))}
      </div>
    </div>
  );
};

export default RenderMultiSelect;