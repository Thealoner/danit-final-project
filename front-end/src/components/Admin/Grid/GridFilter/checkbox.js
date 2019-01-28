import React from 'react';
import PropTypes from 'prop-types';

// simple input doesn't rerender
const Checkbox = ({ type = 'checkbox', name, checked = false, onChange, title, className }) => (
  <input type={type} name={name} checked={checked} onChange={onChange} title={title} className={className} />
);

Checkbox.propTypes = {
  type: PropTypes.string,
  name: PropTypes.string.isRequired,
  checked: PropTypes.bool,
  onChange: PropTypes.func.isRequired,
  title: PropTypes.string,
  className: PropTypes.string
};

export default Checkbox;