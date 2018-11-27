import React, { Component, Fragment } from 'react';
import './index.scss';
import Tabulator from 'tabulator-tables';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import { getEntityByType } from '../GridEntities';
import { Link } from 'react-router-dom';
import Filter from './Filter';
import ajaxRequest from '../../Helpers';

class Grid extends Component {
  constructor (props) {
    super(props);
    this.state = {
      id: '',
      name: '',
      data: [],
      columns: [],
      meta: {
        totalElements: 0,
        currentPage: 0,
        pagesTotal: 0,
        elementsPerPage: 3
      }
    };
  }

  tabulator = null;
  el = React.createRef();

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

  getData = (page = 0, size = 3, filterString = '') => {
    let { entityType } = this.props.match.params;
    let entity = getEntityByType(entityType);

    ajaxRequest(entity.apiUrl + '?page=' + page + '&size=' + size + filterString)
      .then(response => {
        this.props.setTabContentUrl(entity.id);
        this.setState({
          id: entityType,
          data: response.data,
          columns: entity.columns,
          meta: response.meta
        });
      })
      .catch(error => {
        console.log('' + error);
        this.setState({
          id: '',
          data: [],
          columns: [],
          meta: {
            totalElements: 0,
            currentPage: 0,
            pagesTotal: 0,
            elementsPerPage: 3
          }
        });
      });
  };

  pageNext = () => {
    this.getData(this.state.meta.currentPage + 1, this.state.meta.elementsPerPage);
  }

  pagePrev = () => {
    this.getData(this.state.meta.currentPage - 1, this.state.meta.elementsPerPage);
  }

  applyFilter = (filter) => {
    let filterString = '&' + filter.field + '=' + filter.value;
    this.getData(0, 20, filterString);
  }

  clearFilter = () => {
    this.getData();
  }

  render () {
    let { entityType, tabKey } = this.props.match.params;
    let { setTabContentUrl } = this.props;
    let { currentPage, pagesTotal } = this.state.meta;
    setTabContentUrl(entityType);

    return (
      <Fragment>
        <Filter applyFilter={this.applyFilter} clearFilter={this.clearFilter} columns={this.state.columns} />
        <div ref={el => (this.el = el)} className="custom-css-class" data-custom-attr="test-custom-attribute" />
        <Link to={'/admin/' + tabKey + '/' + entityType + '/add'}>Add {entityType}</Link>
        <button onClick={this.pagePrev} disabled={currentPage <= 0}>Previous Page</button>
        <button onClick={this.pageNext} disabled={currentPage >= pagesTotal}>Next Page</button>
      </Fragment>
    );
  }

  componentDidMount () {
    this.getData();
    this.tabulator = new Tabulator(this.el, {
      data: this.state.data,
      columns: this.state.columns,
      rowClick: this.rowClick,
      movableRows: false,
      layout: 'fitColumns'
    });
  }

  componentDidUpdate () {
    this.tabulator.setColumns(this.state.columns);
    this.tabulator.setData(this.state.data);

    let { entityType } = this.props.match.params;

    if (this.state.id !== '' && entityType !== this.state.id) {
      this.getData();
    }
  }
}

export default Grid;