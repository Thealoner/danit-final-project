import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
  cancelEditFormData,
  saveFormData,
  addRecord,
  deleteCurrentEntityItem,
  storeTabTmpFormData
} from '../../../actions/tabActions';
import Paket from './Paket';
import Service from './Service';

class EntityEditor extends Component {
  onSubmit = values => {
    let { currentTab, saveData, addRecord } = this.props;

    if (currentTab.form.data && currentTab.form.data.id) {
      saveData(currentTab.tabKey, values, currentTab.grid.columns, 'edit', 1);
    } else {
      addRecord(currentTab.tabKey, values, currentTab.grid.columns, 'edit', 1);
    }
  }

  onDelete = () => {
    let { deleteData, currentTab } = this.props;
    deleteData(currentTab.tabKey, currentTab.form.data, currentTab.grid.columns, 1);
  }
  
  render () {
    let { currentTab, storeTmpFormData, cancelData } = this.props;

    switch (currentTab.tabKey) {
      case 'pakets':
        return <Paket onSubmit={this.onSubmit} handleChange={storeTmpFormData} handleDelete={this.onDelete} handleCancel={cancelData} currentTab={currentTab}/>;
      case 'services':
        return <Service onSubmit={this.onSubmit} handleChange={storeTmpFormData} handleDelete={this.onDelete} handleCancel={cancelData} currentTab={currentTab}/>;
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
    addRecord: (tabKey, formData, columns, mode, page) => {
      dispatch(addRecord(tabKey, formData, columns, mode, page));
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