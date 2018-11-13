import React, { Fragment, Component } from 'react';

class ClientEditor extends Component {
  render () {
    let { data, handleInputChange } = this.props;

    return (
      <Fragment>
        <label>
          Имя:
          <input type='text' name='firstName' value={data.firstName} onChange={handleInputChange} />
        </label>
        <label>
          Фамилия:
          <input type='text' name='lastName' value={data.lastName} onChange={handleInputChange} />
        </label>
        <label>
          Пол:
          <input type='text' name='gender' value={data.gender} onChange={handleInputChange} />
        </label>
        <label>
          Дата Рождения:
          <input type='text' name='birthDate' value={data.birthDate} onChange={handleInputChange} />
        </label>
        <label>
          Телефон:
          <input type='text' name='phoneNumber' value={data.phoneNumber} onChange={handleInputChange} />
        </label>
        <label>
          Email:
          <input type='text' name='email' value={data.email} onChange={handleInputChange} />
        </label>
      </Fragment>
    );
  }
};

export default ClientEditor;