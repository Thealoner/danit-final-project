import React, {Fragment, Component} from 'react';

class ClientEditor extends Component {
  render () {
    let {data, handleInputChange} = this.props;

    return (
      <Fragment>
        <div className="client__info-block">
          <table>
            <colgroup>
              <col/>
              <col/>
              <col/>
              <col/>
            </colgroup>
            <caption>Основное</caption>
            <tbody>
              <tr>
                <td><label htmlFor='lastName'>Фамилия:</label></td>
                <td><input type='text' name='lastName' value={data.lastName} onChange={handleInputChange} id='lastName'/></td>
                <td><label htmlFor='firstName'>Имя:</label></td>
                <td><input type='text' name='firstName' value={data.firstName} onChange={handleInputChange} id='firstName'/></td>
                <td><label htmlFor='patronymic'>Отчество:</label></td>
                <td><input type='text' name='patronymic' value='' onChange={handleInputChange} id='patronymic'/></td>
              </tr>
              <tr>
                <td><label htmlFor='gender'>Пол:</label></td>
                <td>
                  <select name="gender" value={data.gender} onChange={handleInputChange} id="gender">
                    <option value="Male">Мужской</option>
                    <option value="Female">Женский</option>
                  </select>
                </td>
                <td><label htmlFor='birthDate'>Дата Рождения:</label></td>
                <td><input type='date' name='birthDate' value={data.birthDate} onChange={handleInputChange} id='birthDate'/></td>
              </tr>
            </tbody>
          </table>
        </div>
        <div className='client__info-block'>
          <table>
            <colgroup>
              <col/>
              <col/>
              <col/>
              <col/>
            </colgroup>
            <caption>Контактная информация</caption>
            <tbody>
              <tr>
                <td><label htmlFor='phoneNumber'>Телефон:</label></td>
                <td><input type='text' name='phoneNumber' value={data.phoneNumber} onChange={handleInputChange} id='phoneNumber'/></td>
                <td><label htmlFor='email'>Email:</label></td>
                <td><input type='email' name='email' value={data.email} onChange={handleInputChange} id='email'/></td>
              </tr>
              <tr>
                <td><label htmlFor='address'>Адрес:</label></td>
                <td colSpan='3'><input type='text' name='address' value='г. Киев, ул. Тычины 1, дом 1, кв. 13' onChange={handleInputChange} id='address'/></td>
              </tr>
            </tbody>
          </table>
        </div>
      </Fragment>
    );
  }
}

export default ClientEditor;