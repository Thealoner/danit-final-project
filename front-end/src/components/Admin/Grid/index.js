import React, { Component, Fragment } from 'react';
import Tabulator from 'tabulator-tables';
import './index.scss';
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
        currentPage: 1,
        pagesTotal: 1,
        elementsPerPage: 5
      }
    };
  }

  tabulator = null;
  el = React.createRef();

  rowClick = (e, row) => {
    const { entityType, tabKey } = this.props.match.params;
    this.props.setTabContentUrl(entityType + '/' + row.getData().id);
    this.props.history.push({
      pathname: '/admin/' + tabKey + '/' + entityType + '/edit/' + row.getData().id,
      state: {
        rowData: row.getData(),
        entityType: entityType
      }
    });
  };

  getData = (page = 1, size = 5, filterString = '') => {
    const { entityType } = this.props.match.params;
    const entity = getEntityByType(entityType);

    ajaxRequest(entity.apiUrl + '?page=' + page + '&size=' + size + filterString)
      .then(response => {
        this.props.setTabContentUrl(entity.id);
          
        // Temporary fix, until all entities are returned with data and meta wrappers from server;
        if (response.data === undefined) {
          response.data = response;
          response.meta = {
            totalElements: 0,
            currentPage: 1,
            pagesTotal: 1,
            elementsPerPage: 5
          };
        }
        
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
            currentPage: 1,
            pagesTotal: 1,
            elementsPerPage: 5
          }
        });
      });
  };

  pageNext = () => {
    this.getData(this.state.meta.currentPage + 1, this.state.meta.elementsPerPage);
  };

  pagePrev = () => {
    this.getData(this.state.meta.currentPage - 1, this.state.meta.elementsPerPage);
  };

  applyFilter = (filterString) => {
    this.getData(0, 20, filterString);
  };

  clearFilter = () => {
    this.getData();
  };

  render () {
    const { entityType, tabKey } = this.props.match.params;
    const { setTabContentUrl } = this.props;
    const { currentPage, pagesTotal } = this.state.meta;
    setTabContentUrl(entityType);

    return (
      <Fragment>
        <Filter applyFilter={this.applyFilter} clearFilter={this.clearFilter} columns={this.state.columns} />
        <div ref={el => (this.el = el)} className="tabulator" data-custom-attr="test-custom-attribute" />
        <Link to={'/admin/' + tabKey + '/' + entityType + '/add'}>Add {entityType}</Link>
        <button onClick={this.pagePrev} disabled={currentPage <= 1}>Previous Page</button>
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
      layout: 'fitDataFill'
    });
  }

  componentDidUpdate () {
    this.tabulator.setColumns(this.state.columns);
    this.tabulator.setData(this.state.data);

    const { entityType } = this.props.match.params;

    if (this.state.id !== '' && entityType !== this.state.id) {
      this.getData();
    }
  }
}

export default Grid;