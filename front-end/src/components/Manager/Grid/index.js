import React, { Component, Fragment } from 'react';
import './index.scss';
import { ReactTabulator } from 'react-tabulator';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import axios from 'axios';
import GridEntities from '../GridEntities';

class Grid extends Component {
  state = {
    id: '',
    name: '',
    data: [],
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Дата Рождения', field: 'birthDate' },
      { title: 'Имя', field: 'firstName' },
      { title: 'Фамилия', field: 'lastName' },
      { title: 'Пол', field: 'gender' },
      { title: 'Активен', field: 'active' }
    ]
  };
  ref = null;

  rowClick = (e, row) => {
    let { entityType, tabKey } = this.props.match.params;
    
    this.props.setTabContentUrl(entityType + '/' + row.getData().id);
    
    this.props.history.push({
      pathname: '/manager/' + tabKey + '/' + entityType + '/' + row.getData().id,
      state: {
        rowData: row.getData(),
        entityType: entityType
      }
    });
  };

  setData = () => {
    axios.get('http://localhost:9000/api/clients/all')
      .then(function (response) {
        // handle success
        console.log(response);
        const data = response.data.slice(0, 100);
        this.setState({
          id: this.props.match.params.entityType,
          data: data
          // ,columns: entity.columns
        });
      }.bind(this))
      .catch(function (error) {
        // handle error
        console.log(error);
      })
      .then(function () {
        // always executed
      });
  };

  setSampleData = () => {
    let entityType = this.props.match.params.entityType;
    
    let entity = GridEntities.find((el) => {
      return el.id === entityType;
    });
    
    this.props.setTabContentUrl(entity.id);

    this.setState({
      id: entity.id,
      data: entity.sampleData,
      columns: entity.columns
    });
  };

  clearData = () => {
    this.setState({ data: [] });
  };

  render () {
    const options = {
      movableRows: true,
      layout: 'fitColumns'
    };
    
    return (
      <Fragment>
        <ReactTabulator
          ref={ref => (this.ref = ref)}
          columns={this.state.columns}
          data={this.state.data}
          rowClick={this.rowClick}
          options={options}
          data-custom-attr="test-custom-attribute"
          className="custom-css-class"
        />
        <h3>Asynchronous data: (e.g. fetch) -
          <button onClick={this.setSampleData}>Set Sample Data</button>{' '}
          <button onClick={this.setData}>Set Data From Server</button>{' '}
          <button onClick={this.clearData}>Clear</button>
        </h3>
      </Fragment>
    );
  }

  componentDidMount () {
    this.setSampleData();
  }

  componentDidUpdate () {
    let entityType = this.props.match.params.entityType;
    
    if (entityType !== this.state.id) {
      this.setSampleData();
    }
  }
}

export default Grid;