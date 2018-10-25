import React, {Component, Fragment} from 'react';
import PreLoader from './components/PreLoader';
import {ReactTabulator} from 'react-tabulator';
import 'react-tabulator/lib/styles.css';
import Tabulator from 'tabulator-tables';
import 'tabulator-tables/dist/css/tabulator.min.css';
import './App.scss';

const columns = [
  { title: 'Name', field: 'name', width: 150 },
  { title: 'BirthDate', field: 'birthDate', align: 'left' },
  { title: 'PhoneNumber', field: 'phoneNumber' },
  { title: 'CardId', field: 'cardId', align: 'center' }
];

const data = [
  { id: 1, name: 'Dmitriy Novikov', birthDate: '18.07.1987', phoneNumber: '+380956358965', cardId: '265987456' },
  { id: 2, name: 'Alexey Fateev', birthDate: '13.04.1984', phoneNumber: '+380638954782', cardId: '852410003' },
  { id: 3, name: 'Serhii Vakulenko', birthDate: '01.01.1999', phoneNumber: '+380508961200', cardId: '596001478' },
  { id: 4, name: 'Serhii Harmash', birthDate: '12.02.1998', phoneNumber: '+380448907685', cardId: '768900112' }
];

class App extends Component {
  render () {
    return (
      <Fragment>
        <div className="App-preloader" ref="preloader"><PreLoader fullScreen={false}/></div>
        <div className="App">
          <ReactTabulator />
        </div>
      </Fragment>
    );
  }

  componentDidMount () {
    let self = this;
    setTimeout(function () {
      self.refs.preloader.style.opacity = '0';
      self.refs.preloader.style.visibility = 'hidden';
    }, 2000);
    setTimeout(function () {
      self.refs.preloader.style.display = 'none';
    }, 3000);
  }
}

export default App;
