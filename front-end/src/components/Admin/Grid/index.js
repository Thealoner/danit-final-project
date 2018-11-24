import React, { Component, Fragment } from 'react';
import './index.scss';
import Tabulator from 'tabulator-tables';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import { getEntityByType } from '../GridEntities';
import AuthService from '../../Login/AuthService';
import Settings from '../../Settings';
import { Link } from 'react-router-dom';

class Grid extends Component {
  constructor(props) {
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
    }
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

  getData = (page, size) => {
    let { entityType } = this.props.match.params;
    let entity = getEntityByType(entityType);
    let authService = new AuthService();

    if (authService.loggedIn() && !authService.isTokenExpired()) {
      const headers = {
        'Content-Type': 'application/json'
      };

      let token = authService.getToken();
      headers['Authorization'] = token;

      fetch(
        Settings.apiServerUrl + entity.apiUrl + '?page=' + (page || 0) + '&size=' + (size || 3),  
        { headers }
      )
        .then(authService._checkStatus)
        .then(response => response.json())
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
    } else {
      console.log('Not logged in or token is expired');
    }
  };

  pageNext = () => {
    this.getData(this.state.meta.currentPage + 1, this.state.meta.elementsPerPage);
  }

  pagePrev = () => {
    this.getData(this.state.meta.currentPage - 1, this.state.meta.elementsPerPage);
  }

  render() {
    let { entityType, tabKey } = this.props.match.params;
    let { setTabContentUrl } = this.props;
    let { currentPage, pagesTotal } = this.state.meta;
    setTabContentUrl(entityType);

    return (
      <Fragment>
        <div ref={el => (this.el = el)} className="custom-css-class" data-custom-attr="test-custom-attribute" />
        <Link to={'/admin/' + tabKey + '/' + entityType + '/add'}>Add {entityType}</Link>
        <button onClick={this.pagePrev} disabled={currentPage <= 0}>Previous Page</button>
        <button onClick={this.pageNext} disabled={currentPage >= pagesTotal}>Next Page</button>
      </Fragment>
    );
  }

  componentDidMount() {
    this.getData();
    this.tabulator = new Tabulator(this.el, {
      data: this.state.data,
      columns: this.state.columns,
      rowClick: this.rowClick,
      movableRows: false,
      layout: 'fitColumns'
    });
  }

  componentDidUpdate() {
    this.tabulator.setColumns(this.state.columns);
    this.tabulator.setData(this.state.data);

    let { entityType } = this.props.match.params;
    if (this.id !== '' && entityType !== this.id) {
      this.getData();
    }
  }
}

export default Grid;