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
  // onSubmit = data => {
  //   let { currentTab, saveData } = this.props;
  //   debugger;
  //   saveData(currentTab.tabKey, data, currentTab.grid.columns, 'edit', 1);
  // }
  
  render () {
    let { currentTab, onSubmit } = this.props;

    switch (currentTab.tabKey) {
      case 'services':
        return <Service onSubmit={onSubmit} currentTab={currentTab}/>;
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
    },
    onSubmit: values => {
      console.log(values);
      debugger;
    }
  };
};

export default connect(null, mapDispatchToProps)(EntityEditor);