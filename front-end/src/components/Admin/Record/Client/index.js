import React, { Component } from 'react';
import './index.scss';
import { ReactTabulator } from 'react-tabulator';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import { getEntityByType } from '../../gridEntities';
import AuthService from '../../../Login/AuthService';
import photo from './photo.jpg';
import ajaxRequest from '../../../../helpers/ajaxRequest';
import autoSize from 'autosize';
import {toastr} from 'react-redux-toastr';

class SimpleRecord extends Component {
  constructor (props) {
    super(props);
    this.state = {
      entityType: '',
      name: '',
      editableFields: {
        id: '',
        firstName: '',
        lastName: '',
        gender: '',
        birthDate: '',
        phoneNumber: '',
        email: '',
        active: false
      },
      readonlyFields: {
        contracts: []
      },
      authService: new AuthService(),
      loading: false,
      messageType: '',
      messageText: ''
    };
  }

  getData = () => {
    const { rowId } = this.props.match.params;
    const { entityType } = this.props;
    const entity = getEntityByType(entityType);
    const textareas = document.getElementsByTagName('textarea');

    this.setState({
      loading: true
    });

    if (this.state.authService.loggedIn() && !this.state.authService.isTokenExpired()) {
      ajaxRequest(entity.apiUrl + '/' + rowId)
        .then(data => {
          const editableDataKeys = Object.keys(this.state.editableFields);
          const editableData = {};

          editableDataKeys.forEach(key => {
            editableData[key] = data[key];
          });

          const readonlyDataKeys = Object.keys(this.state.readonlyFields);
          const readonlyData = {};

          readonlyDataKeys.forEach(key => {
            readonlyData[key] = data[key];
          });

          this.setState({
            entityType: entity.id,
            editableFields: editableData,
            readonlyFields: readonlyData,
            loading: false
          });

          autoSize(textareas);
        });
    } else {
      toastr.error('Not logged in or token is expired');
    }
  };

  saveData = () => {
    const { entityType } = this.props;
    const entity = getEntityByType(entityType);

    this.setState({
      loading: true,
      messageType: ''
    });

    ajaxRequest(
      entity.apiUrl,
      'PUT',
      JSON.stringify([this.state.editableFields])
    )
      .then(response => {
        this.setState({
          loading: false
        });
        toastr.success('Данные успешно сохранены', response.status);
      })
      .catch(error => {
        this.setState({
          loading: false
        });
        toastr.error('Ошибка при сохранении', error);
      });
  };

  handleInputChange = (event) => {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    this.setState(prevState => ({
      editableFields: {
        ...prevState.editableFields,
        [name]: value
      }
    }));
  };

  render () {
    const { mode, rowId } = this.props.match.params;
    const { entityType, setTabContentUrl } = this.props;
    setTabContentUrl(entityType + '/' + mode + '/' + rowId);

    const options = {
      movableRows: true
    };

    const columns = [
      { title: 'ID', field: 'id' },
      { title: 'Пакет', field: 'packageId' },
      { title: 'startDate', field: 'startDate' },
      { title: 'endDate', field: 'endDate' },
      { title: 'Активен', field: 'active' }
    ];

    return (
      <div className="client">
        <div className="client__container">
          <div className='client__block client__block--general'>
            <h4 className="client__block-title">Основное</h4>
            <div className='record__data-field'>
              <label htmlFor='lastName'>Фамилия:</label>
              <input type='text' name='lastName' value={this.state.editableFields.lastName} onChange={this.handleInputChange} id='lastName'/>
            </div>
            <div className='record__data-field'>
              <label htmlFor='firstName'>Имя:</label>
              <input type='text' name='firstName' value={this.state.editableFields.firstName} onChange={this.handleInputChange} id='firstName'/>
            </div>
            <div className='record__data-field'>
              <label htmlFor='patronymic'>Отчество:</label>
              <input type='text' name='patronymic' defaultValue='Ivanov' onChange={this.handleInputChange} id='patronymic'/>
            </div>
            <div className='record__data-field'>
              <label htmlFor='gender'>Пол:</label>
              <select name="gender" value={this.state.editableFields.gender} onChange={this.handleInputChange} id="gender">
                <option value="Male">Мужской</option>
                <option value="Female">Женский</option>
              </select>
            </div>
            <div className='record__data-field'>
              <label htmlFor='birthDate'>Дата Рождения:</label>
              <input type='date' name='birthDate' value={this.state.editableFields.birthDate} onChange={this.handleInputChange} id='birthDate'/>
            </div>
          </div>
          <div className='client__block client__block--contact'>
            <h4 className="client__block-title">Контактная информация</h4>
            <div className='record__data-field'>
              <label htmlFor='phoneNumber'>Телефон:</label>
              <input type='text' name='phoneNumber' value={this.state.editableFields.phoneNumber} onChange={this.handleInputChange} id='phoneNumber'/>
            </div>
            <div className='record__data-field'>
              <label htmlFor='email'>Email:</label>
              <input type='email' name='email' value={this.state.editableFields.email} onChange={this.handleInputChange} id='email'/>
            </div>
            <div className='record__data-field record__data-field--address'>
              <label htmlFor='address'>Адрес:</label>
              <input type='text' name='address' defaultValue='г. Киев, ул. Тычины 1, дом 1, кв. 13' onChange={this.handleInputChange} id='address'/>
            </div>
          </div>
          <div className='client__block client__block--contracts'>
            <h4 className="client__block-title">Контракты</h4>
            <ReactTabulator
              ref={ref => (this.ref = ref)}
              columns={columns}
              data={this.state.readonlyFields.contracts}
              rowClick={this.rowClick}
              options={options}
              data-custom-attr="test-custom-attribute"
              className="custom-css-class"
            />
          </div>
          <div className='client__block client__block--registration'>
            <h4 className="client__block-title">Оформление</h4>
            <div className='record__data-field'>
              <label htmlFor='phoneNumber'>Организация:</label>
              <select name="organization" defaultValue='Breeze' onChange={this.handleInputChange}
                id="organization">
                <option value="Breeze">Бриз</option>
                <option value="TradeWind">Пассат</option>
                <option value="Monsoon">Муссон</option>
              </select>
            </div>
            <div className='record__data-field'>
              <label htmlFor='email'>Дата оформления:</label>
              <input type='date' name='registrationDate' onChange={this.handleInputChange} id='registrationDate'/>
            </div>
            <div className='record__data-field'>
              <label htmlFor='manager'>Менеджер:</label>
              <input type='text' name='manager' defaultValue='Вовк Марина' onChange={this.handleInputChange} id='manager'/>
            </div>
          </div>
          <div className='client__block client__block--additionally'>
            <h4 className="client__block-title">Дополнительно</h4>
            <div className='record__data-field'>
              <label htmlFor='phoneNumber'>Источник информации:</label>
              <select name="infoSource" defaultValue='Breeze' onChange={this.handleInputChange}
                id="infoSource">
                <option value="Employee Breeze">Сотрудник Бриз</option>
              </select>
            </div>
          </div>
          <div className='client__block client__block--main'>
            <div className='client__photo-wrapper'>
              <img src={photo} alt='Фото клиента' className='client__photo'/>
            </div>
            <div className='record__data-field'>
              <label htmlFor='clientID'>Карта(ID)</label>
              <input type='text' name='id' value={this.state.editableFields.id} id='clientID' readOnly/>
            </div>
            <div className='record__data-field'>
              <label htmlFor='clientCategory'>Категория клиента</label>
              <select name="clientCategory" defaultValue='SPECIAL ONE' onChange={this.handleInputChange}
                id="clientCategory">
                <option value="LIFE STYLE">LIFE STYLE</option>
                <option value="SPECIAL ONE">SPECIAL ONE</option>
                <option value="SPECIAL ONE Ранок">SPECIAL ONE Ранок</option>
              </select>
            </div>
            <div className='record__data-field'>
              <label htmlFor='clientID'>Текущий баланс</label>
              <input type='text' name='balance' value='300 грн' id='balance' readOnly/>
            </div>
            <div className='record__data-field'>
              <label htmlFor='notes'>Примечания</label>
              <textarea name='notes' id='notes' onChange={this.handleInputChange}
                defaultValue='Lorem ipsum dolor sit amet temp, consectetur adipisicing elit, se do eiusmod tempor incididunt ut labore et.'/>
            </div>
          </div>
        </div>
        <button disabled={this.state.loading} onClick={this.saveData} className="record__button">Сохранить</button>
      </div>
    );
  }

  componentDidMount () {
    this.getData();
  }
}

export default SimpleRecord;