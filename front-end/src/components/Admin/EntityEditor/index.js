import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
  cancelEditFormData,
  saveFormData,
  deleteCurrentEntityItem,
  storeTabTmpFormData
} from '../../../actions/tabActions';
import Service from './Service';

class EntityEditor extends Component {
  onSubmit = values => {
    let { currentTab } = this.props;
    this.props.saveData(currentTab.tabKey, values, currentTab.grid.columns, 'edit', 1);
  }
  
  render () {
    let { currentTab } = this.props;

    switch (currentTab.tabKey) {
      case 'services':
        return <Service onSubmit={this.onSubmit} currentTab={currentTab}/>;
      default:
        return <h1>Form component for this entity is not defined.</h1>;
    }
  }
};

const mapDispatchToProps = dispatch => {
  return {
    storeTmpFormData: (payload) => {
      dispatch(storeTabTmpFormData(payload));
    },
    saveData: (tabKey, formData, columns, mode, page) => {
      dispatch(saveFormData(tabKey, formData, columns, mode, page));
    },
    deleteData: (tabKey, formData, columns, page) => {
      dispatch(deleteCurrentEntityItem(tabKey, formData, columns, page));
    },
    cancelData: () => {
      dispatch(cancelEditFormData());
    }
  };
};

export default connect(null, mapDispatchToProps)(EntityEditor);