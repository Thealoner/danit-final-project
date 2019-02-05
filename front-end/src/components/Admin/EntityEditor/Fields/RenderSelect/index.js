import React from 'react';
import { Dropdown } from 'semantic-ui-react';

const RenderSelect = ({
  input,
  label,
  type,
  name,
  options,
  changeField,
  required,
  meta: { touched, error, warning }
}) => (
  <div className="form-group field field-string">
    <label className="control-label">{label} <span className="field_required">{required ? '*' : ''}</span></label>
    <div>
      <Dropdown value={input.value} fluid selection options={options} onChange={changeField} className={error ? 'field_error-input' : null || warning ? 'field_warning-input' : null} />
      {touched &&
        ((error && <div className="field_error">{error}</div>) ||
          (warning && <div className="field_warning">{warning}</div>))}
    </div>
  </div>
);

export default RenderSelect;