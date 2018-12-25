import React, { Component } from 'react';
import './index.scss';
import Tabulator from 'tabulator-tables';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import { getEntityByType } from '../gridEntities';
import ajaxRequest from '../../../helpers/ajaxRequest';
import {toastr} from 'react-redux-toastr';

class Grid extends Component {
    el = React.createRef();
    id = '';
    name = '';
    data = [];
    columns = [];
    tabulator = null;

    rowClick = (e, row) => {
      const { entityType, tabKey } = this.props.match.params;
      this.props.setTabContentUrl(entityType + '/' + row.getData().id);
      this.props.history.push({
        pathname: '/manager/' + tabKey + '/' + entityType + '/' + row.getData().id,
        state: {
          rowData: row.getData(),
          entityType: entityType
        }
      });
    };

    getData = () => {
      const { entityType } = this.props.match.params;
      const entity = getEntityByType(entityType);

      ajaxRequest(entity.apiUrl)
        .then(data => {
          this.props.setTabContentUrl(entity.id);
          this.id = entityType;
          this.data = data;
          this.columns = entity.columns;
          this.tabulator.setColumns(this.columns);
          this.tabulator.setData(this.data);
        })
        .catch(error => {
          toastr.error(error.message);
          this.id = '';
          this.data = [];
          this.columns = [];
          this.tabulator.setColumns(this.columns);
          this.tabulator.setData(this.data);
        });
    };

    render () {
      const { entityType } = this.props.match.params;
      const { setTabContentUrl } = this.props;
      setTabContentUrl(entityType);

      return (
        <div ref={el => (this.el = el)} className="custom-css-class" data-custom-attr="test-custom-attribute"/>
      );
    }

    componentDidMount () {
      this.getData();
      this.tabulator = new Tabulator(this.el, {
        data: this.data,
        columns: this.columns,
        rowClick: this.rowClick,
        movableRows: true,
        layout: 'fitColumns'
      });
    }

    componentDidUpdate () {
      const { entityType } = this.props.match.params;

      if (entityType !== this.id) {
        this.getData();
      }
    }
}

export default Grid;