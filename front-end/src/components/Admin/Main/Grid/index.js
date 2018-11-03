import React, { Component, Fragment } from 'react';
import './index.scss';
import { ReactTabulator } from 'react-tabulator';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import axios from 'axios';
import GridEntities from '../GridEntities';

class Packages extends Component {
  state = {
    id: '',
    name: '',
    data: [],
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Title', field: 'title' },
      { title: 'Price', field: 'price', align: 'left' },
      { title: 'Active', field: 'active' }
    ]
  };
  ref = null;

  rowClick = (e, row) => {
    console.log('ref table: ', this.ref.table); // this is the Tabulator table instance
    console.log('rowClick id:' + row.getData().id, row, e);
  };

  setData = () => {
    axios.get('http://localhost:9000/api/clients/all')
      .then(function (response) {
        // handle success
        console.log(response);
        const data = response.data.slice(0, 100);
        this.setState({
          id: this.props.match.params.entityId,
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
    let entityId = this.props.match.params.entityId;
    
    let entity = GridEntities.find((el) => {
      return el.id === entityId;
    });

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
    let entityId = this.props.match.params.entityId;
    
    if (entityId !== this.state.id) {
      this.setSampleData();
    }
  }
}

export default Packages;