import React, { Component, Fragment } from 'react';
import './index.scss';
import Tabulator from 'tabulator-tables';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import { getEntityByType } from '../GridEntities';
import { Link } from 'react-router-dom';
import ajaxRequest from '../../Helpers';

class Grid extends Component {
    el = React.createRef();
    id = '';
    name = '';
    data = [];
    columns = [];
    tabulator = null;

    rowClick = (e, row) => {
      let { entityType, tabKey } = this.props.match.params;
      this.props.setTabContentUrl(entityType + '/' + row.getData().id);
      this.props.history.push({
        pathname: '/admin/' + tabKey + '/' + entityType + '/edit/' + row.getData().id,
        state: {
          rowData: row.getData(),
          entityType: entityType
        }
      });
    };

    getData = () => {
      let { entityType } = this.props.match.params;
      let entity = getEntityByType(entityType);

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
          console.log('' + error);
          this.id = '';
          this.data = [];
          this.columns = [];
          this.tabulator.setColumns(this.columns);
          this.tabulator.setData(this.data);
        });
    };

    render () {
      let { entityType, tabKey } = this.props.match.params;
      let { setTabContentUrl } = this.props;
      setTabContentUrl(entityType);

      return (
        <Fragment>
          <div ref={el => (this.el = el)} className="custom-css-class" data-custom-attr="test-custom-attribute"/>
          <Link to={'/admin/' + tabKey + '/' + entityType + '/add'}>Add {entityType}</Link>
        </Fragment>
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
      let { entityType } = this.props.match.params;

      if (entityType !== this.id) {
        this.getData();
      }
    }
}

export default Grid;