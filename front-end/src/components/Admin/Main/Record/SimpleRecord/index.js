import React, { Component } from 'react';
import './index.scss';
import { ReactTabulator } from 'react-tabulator';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';

class SimpleRecord extends Component {
  state = {
    id: '',
    name: '',
    columns: [
      { title: 'ID', field: 'id', width: 150 },
      { title: 'Title', field: 'title' },
      { title: 'Price', field: 'price', align: 'left' },
      { title: 'Active', field: 'active' }
    ]
  };

  render () {
    const options = {
      height: 300,
      movableRows: true
    };
    let data = this.props.location.state.rowData;

    return (

      <ReactTabulator
        ref={ref => (this.ref = ref)}
        columns={this.state.columns}
        data={data}
        rowClick={this.rowClick}
        options={options}
        data-custom-attr="test-custom-attribute"
        className="custom-css-class"
      />
    );
  }

  componentDidMount () {
    
  }
}

export default SimpleRecord;