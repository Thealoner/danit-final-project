import React, { Component } from 'react';
import './index.scss';
import { ReactTabulator } from 'react-tabulator';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import GridEntities from '../../../gridEntities';

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
    
    let { entityType, setTabContentUrl } = this.props;
    let rowId = this.props.match.params.rowId;
    
    setTabContentUrl(entityType + '/' + rowId);

    let entity = GridEntities.find((el) => {
      return el.id === entityType;
    });
    
    let dataRecord = entity.sampleData.find((el) => {
      return el.id === +rowId;
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