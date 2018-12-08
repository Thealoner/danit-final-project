import React, {Component, Fragment} from 'react';
import Tabulator from 'tabulator-tables';
import './index.scss';
import {getEntityByType} from '../gridEntities';
import {Link} from 'react-router-dom';
import Filter from './Filter';
import ajaxRequest from '../../../helpers/ajaxRequest';
import {toastr} from 'react-redux-toastr';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import { Pagination } from 'react-bootstrap';

const defaultMeta = {
  totalElements: 0,
  currentPage: 1,
  pagesTotal: 1,
  elementsPerPage: 2
};
class Grid extends Component {
  constructor (props) {
    super(props);
    this.state = {
      id: '',
      name: '',
      data: [],
      columns: [],
      meta: {
        ...defaultMeta
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

  getData = (page = 1, size = 2, filterString = '') => {
    const { entityType } = this.props.match.params;
    const entity = getEntityByType(entityType);

    ajaxRequest(entity.apiUrl + '?page=' + page + '&size=' + size + filterString)
      .then(response => {
        this.props.setTabContentUrl(entity.id);

        // Temporary fix, until all entities are returned with data and meta wrappers from server;
        if (response.data === undefined) {
          response.data = response;
          response.meta = {
            ...defaultMeta
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
        toastr.error(error);
        this.setState({
          id: '',
          data: [],
          columns: [],
          meta: {
            ...defaultMeta
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
    const { currentPage, pagesTotal, elementsPerPage } = this.state.meta;
    const paginationPages = [];
    setTabContentUrl(entityType);

    if (currentPage > 2) {
      paginationPages.push(
        <Pagination.Item onClick={() => this.getData( currentPage-1, elementsPerPage )}>{currentPage-1}</Pagination.Item>
      );
    }

    if (currentPage > 1 && currentPage < pagesTotal) {
      paginationPages.push(
        <Pagination.Item onClick={() => this.getData( currentPage, elementsPerPage )}>{currentPage}</Pagination.Item>
      );
    }
    if (currentPage < pagesTotal - 2) {
      paginationPages.push(
        <Pagination.Item onClick={() => this.getData( currentPage+1, elementsPerPage )}>{currentPage+1}</Pagination.Item>
      );
    }

    return (
      <Fragment>
        <Filter applyFilter={this.applyFilter} clearFilter={this.clearFilter} columns={this.state.columns}/>
        <div ref={el => (this.el = el)} className="grid" data-custom-attr="test-custom-attribute"/>
        <div className="grid-footer">
          <Link to={'/admin/' + tabKey + '/' + entityType + '/add'} className="grid-footer__add-btn">
            <FontAwesomeIcon className="header__plus-icon" icon="plus" size="1x"/>
            Добавить {getEntityByType(entityType).nameForAddBtn}</Link>
          <Pagination>
            <Pagination.Prev onClick={this.pagePrev} disabled={currentPage <= 1}/>
            <Pagination.Item onClick={() => this.getData(1, elementsPerPage)}>{1}</Pagination.Item>
            <Pagination.Ellipsis />
            {paginationPages}
            <Pagination.Ellipsis />
            <Pagination.Item onClick={() => this.getData(pagesTotal, elementsPerPage)}>{pagesTotal}</Pagination.Item>
            <Pagination.Next onClick={this.pageNext} disabled={currentPage >= pagesTotal}/>

          </Pagination>
        </div>
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