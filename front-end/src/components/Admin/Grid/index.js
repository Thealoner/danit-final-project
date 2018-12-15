import React, {Component, Fragment} from 'react';
import Tabulator from 'tabulator-tables';
import './index.scss';
import {getEntityByType} from '../gridEntities';
import {Link} from 'react-router-dom';
import Filter from './Filter';
import ajaxRequest from '../../../helpers/ajaxRequest';
import {toastr} from 'react-redux-toastr';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import { Pagination } from 'semantic-ui-react';

const defaultMeta = {
  totalElements: 0,
  currentPage: 1,
  pagesTotal: 1,
  elementsPerPage: 3
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
      },
      showEllipsis: true,
      showFirstAndLastNav: true,
      showPreviousAndNextNav: true
    };
  }

  tabulator = null;
  tabulatorTable = React.createRef();

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

  getData = (page = 1, size = 3, filterString = '') => {
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

  applyFilter = (filterString) => {
    this.getData(0, 30, filterString);
  };

  clearFilter = () => {
    this.getData();
  };

  handlePaginationChange = (e, { activePage }) => {
    this.getData(activePage, this.state.meta.elementsPerPage);
  };

  render () {
    const { entityType, tabKey } = this.props.match.params;
    const { setTabContentUrl } = this.props;
    const { currentPage, pagesTotal } = this.state.meta;
    const {showEllipsis, showFirstAndLastNav, showPreviousAndNextNav} = this.state;
    setTabContentUrl(entityType);

    return (
      <Fragment>
        <Filter applyFilter={this.applyFilter} clearFilter={this.clearFilter} columns={this.state.columns}/>
        <div ref={el => (this.tabulatorTable = el)} className="tabulator" data-custom-attr="test-custom-attribute"/>
        <div className="grid-footer">
          <Link to={'/admin/' + tabKey + '/' + entityType + '/add'} className="grid-footer__add-btn">
            <FontAwesomeIcon className="grid-footer__plus-icon" icon="plus" size="1x"/>
            Добавить {getEntityByType(entityType).nameForAddBtn}</Link>
          <Pagination
            activePage={currentPage}
            boundaryRange={1}
            onPageChange={this.handlePaginationChange}
            siblingRange={1}
            size='mini'
            totalPages={pagesTotal}
            ellipsisItem={showEllipsis ? undefined : null}
            firstItem={showFirstAndLastNav ? undefined : null}
            lastItem={showFirstAndLastNav ? undefined : null}
            prevItem={showPreviousAndNextNav ? undefined : null}
            nextItem={showPreviousAndNextNav ? undefined : null}
          />
        </div>
      </Fragment>
    );
  }

  componentDidMount () {
    this.getData();
    this.tabulator = new Tabulator(this.tabulatorTable, {
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