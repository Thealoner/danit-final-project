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
import { setTabGridContent } from '../../../actions/tabActions';
import { connect } from 'react-redux';

const defaultMeta = {
  totalElements: 0,
  currentPage: 1,
  pagesTotal: 1,
  elementsPerPage: 1
};

class Grid extends Component {
  state = {
    showEllipsis: true,
    showFirstAndLastNav: true,
    showPreviousAndNextNav: true
  };

  tabulator = null;
  tabulatorTable = React.createRef();

  rowClick = (e, row) => {
    // TODO
  };

  getData = (page = 1, size = 3, filterString = '') => {
    const { currentTab, setTabGridContent } = this.props;
    const entity = getEntityByType(currentTab.tabKey);

    ajaxRequest(entity.apiUrl + '?page=' + page + '&size=' + size + filterString)
      .then(response => {
        setTabGridContent(currentTab.tabKey, {
          data: response.data,
          meta: response.meta,
          columns: entity.columns
        });
      })
      .catch(error => {
        toastr.error(error);
        setTabGridContent(currentTab.tabKey, {
          data: [],
          meta: {},
          columns: entity.columns
        });
      });
  };

  applyFilter = (filterString) => {
    this.getData(0, 20, filterString);
  };

  clearFilter = () => {
    this.getData();
  };

  handlePaginationChange = (e, { activePage }) => {
    const meta = this.props.currentTab.grid.meta;
    this.getData(activePage, meta.elementsPerPage);
  };

  render () {
    const { currentTab } = this.props;
    const { currentPage, pagesTotal } = currentTab.grid.meta;
    const { showEllipsis, showFirstAndLastNav, showPreviousAndNextNav } = this.state;

    return (
      <Fragment>
        <Filter applyFilter={this.applyFilter} clearFilter={this.clearFilter} columns={this.state.columns}/>
        <div ref={el => (this.tabulatorTable = el)} className="tabulator" data-custom-attr="test-custom-attribute"/>
        <div className="grid-footer">
          <Link to={'/admin/todo'} className="grid-footer__add-btn">
            <FontAwesomeIcon className="grid-footer__plus-icon" icon="plus" size="1x"/>
            Добавить {getEntityByType(currentTab.tabKey).nameForAddBtn}</Link>
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
    const { currentTab, setTabContent } = this.props;
    setTabContent(currentTab.tabKey, {
      type: 'grid',
      data: {},
      meta: defaultMeta
    });

    this.getData();
    this.tabulator = new Tabulator(this.tabulatorTable, {
      data: currentTab.grid.data,
      columns: currentTab.grid.columns,
      rowClick: this.rowClick,
      movableRows: false,
      layout: 'fitDataFill'
    });
  }

  componentDidUpdate () {
    const { currentTab } = this.props;
    this.tabulator.setColumns(currentTab.grid.columns);
    this.tabulator.setData(currentTab.grid.data);

    // if (this.state.id !== '' && entityType !== this.state.id) {
    //   this.getData();
    // }
  }
}

const mapDispatchToProps = dispatch => {
  return {
    setTabGridContent: (tabKey, payload) => {
      dispatch(setTabGridContent(tabKey, payload));
    }
  };
};

export default connect(null, mapDispatchToProps)(Grid);