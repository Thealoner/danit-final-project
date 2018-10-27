import React, {Component, Fragment} from 'react';
import './index.scss';
import {ReactTabulator} from 'react-tabulator';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import axios from 'axios';

const columns = [
  {title: 'Name', field: 'firstName', width: 150},
  {title: 'BirthDate', field: 'lastName', align: 'left'},
  {title: 'PhoneNumber', field: 'gender'},
  {title: 'CardId', field: 'cardId', align: 'birthDate'}
];

class Packages extends Component {
    state = {
      data: []
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
        this.setState({data: data});
      }.bind(this))
      .catch(function (error) {
        // handle error
        console.log(error);
      })
      .then(function () {
        // always executed
      });
      
    };
    clearData = () => {
      this.setState({data: []});
    };

    render () {
      const options = {
        height: 150,
        movableRows: true
      };

      return (
        <Fragment>
          <ReactTabulator
            ref={ref => (this.ref = ref)}
            columns={columns}
            data={this.state.data}
            rowClick={this.rowClick}
            options={options}
            data-custom-attr="test-custom-attribute"
            className="custom-css-class"
          />
          <h3>Asynchronous data: (e.g. fetch) - <button onClick={this.setData}>Set Data</button>{' '}
            <button onClick={this.clearData}>Clear</button>
          </h3>
          <ReactTabulator columns={columns} data={this.state.data}/>
        </Fragment>
      );
    }

    componentDidMount() {
      this.setData();
    }
}

export default Packages;