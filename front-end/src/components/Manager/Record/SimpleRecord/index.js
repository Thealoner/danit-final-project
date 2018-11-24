import React, { Component, Fragment } from 'react';
import './index.scss';
import { ReactTabulator } from 'react-tabulator';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import { getEntityByType } from '../../GridEntities';
import ajaxRequest from '../../../Helpers';

class SimpleRecord extends Component {
  state = {
    entityType: '',
    name: '',
    data: [],
    columns: [
      { title: 'Key', field: 'key', width: 150 },
      { title: 'Value', field: 'value', align: 'left', editor: true }
    ]
  };

  getData = () => {
    let { rowId } = this.props.match.params;
    let { entityType } = this.props;
    let entity = getEntityByType(entityType);

    ajaxRequest(entity.apiUrl + '/' + rowId)
      .then(data => {
        let keys = Object.keys(data);
        let dataArray = [];

        keys.forEach((key) => {
          dataArray.push({
            key: key,
            value: data[key]
          });
        });

        this.setState({
          entityType: entityType,
          data: dataArray
          // ,columns: entity.columns
        });
      });
  };

  saveData = () => {
    let { entityType } = this.props;
    let entity = getEntityByType(entityType);

    let array = this.state.data;
    let dataToSave = array.reduce((obj, {key, value}) => ({ ...obj, [key]: value }), {});

    ajaxRequest(
      entity.apiUrl,
      'PUT',
      JSON.stringify(dataToSave)
    )
      .then(data => {
        let keys = Object.keys(data);
        let dataArray = [];

        keys.forEach((key) => {
          dataArray.push({
            key: key,
            value: data[key]
          });
        });

        this.setState({
          entityType: entityType,
          data: dataArray
        });
      });
  };

  render () {
    let { rowId } = this.props.match.params;
    let { entityType, setTabContentUrl } = this.props;
    setTabContentUrl(entityType + '/' + rowId);

    const options = {
      movableRows: true
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
        <button onClick={this.saveData}>Save</button>
      </Fragment>
    );
  }

  componentDidMount () {
    this.getData();
  }
}

export default SimpleRecord;