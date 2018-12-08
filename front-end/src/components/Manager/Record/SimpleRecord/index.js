import React, { Component, Fragment } from 'react';
import './index.scss';
import { ReactTabulator } from 'react-tabulator';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import { getEntityByType } from '../../gridEntities';
import ajaxRequest from '../../../../helpers/ajaxRequest';

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
    const { rowId } = this.props.match.params;
    const { entityType } = this.props;
    const entity = getEntityByType(entityType);

    ajaxRequest(entity.apiUrl + '/' + rowId)
      .then(data => {
        const keys = Object.keys(data);
        const dataArray = [];

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
    const { entityType } = this.props;
    const entity = getEntityByType(entityType);

    const array = this.state.data;
    const dataToSave = array.reduce((obj, {key, value}) => ({ ...obj, [key]: value }), {});

    ajaxRequest(
      entity.apiUrl,
      'PUT',
      JSON.stringify(dataToSave)
    )
      .then(data => {
        const keys = Object.keys(data);
        const dataArray = [];

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
    const { rowId } = this.props.match.params;
    const { entityType, setTabContentUrl } = this.props;
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