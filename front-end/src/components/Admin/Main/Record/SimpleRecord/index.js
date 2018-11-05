import React, { Component } from 'react';
import './index.scss';
import { ReactTabulator } from 'react-tabulator';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import GridEntities from '../../GridEntities';

class SimpleRecord extends Component {
  state = {
    id: '',
    name: '',
    columns: [
      { title: 'Key', field: 'key', width: 150 },
      { title: 'Value', field: 'value', align: 'left' }
    ]
  };

  render () {
    const options = {
      height: 300,
      movableRows: true
    };
    
    // let entityType = this.props.location.state.entityType;
    // let rowData = this.props.location.state.rowData;
    let rowId = this.props.match.params.id;
    let entityType = this.props.match.params.entityType;

    let entity = GridEntities.find((el) => {
      return el.id === entityType;
    });
    
    let dataRecord = entity.sampleData.find((el) => {
      return el.id === rowId;
    });
    
    let keys = Object.keys(dataRecord);
    let data = [];
    
    keys.forEach((key) => {
      data.push({
        key: key,
        value: dataRecord[key]
      });
    });

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