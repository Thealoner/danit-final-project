import React, { Component, Fragment } from 'react';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import Form from 'react-jsonschema-form';
import ajaxRequest from '../../../../helpers/ajaxRequest';
// import resizeInputs from '../../../../helpers/resizeInputs';
import {toastr} from 'react-redux-toastr';
import {connect} from 'react-redux';
import {
  loadingTab,
  doneTab,
  setTabFormData,
  persistTabFormData,
  storeTabTmpFormData
} from '../../../../actions/tabActions';
import { getEntityByType } from '../../gridEntities';

// const formInputs = document.getElementsByClassName('form-control');

class RecordEditor extends Component {
  state = {};

  getData = () => {
    const { currentTab, loadingTab, setFormData } = this.props;
    loadingTab();

    ajaxRequest.get('/' + currentTab.tabKey + '/' + currentTab.form.id)
      .then(data => {
        setFormData(currentTab.tabKey, { data });
        // resizeInputs(formInputs);
      });
  };

  putData = form => {
    const { currentTab, loadingTab, doneTab, persistFormData } = this.props;
    loadingTab();

    ajaxRequest.put(
      '/' + currentTab.tabKey,
      JSON.stringify([form.formData])
    )
      .then(json => {
        persistFormData(currentTab.tabKey, json[0]);
        toastr.success('Данные успешно сохранены');
        // resizeInputs(formInputs);
      })
      .catch(error => {
        doneTab();
        toastr.error('Ошибка при сохранении', error);
      });
  };

  postData = form => {
    const { currentTab, loadingTab, doneTab, persistFormData } = this.props;
    loadingTab();
    
    ajaxRequest.post(
      '/' + currentTab.tabKey,
      JSON.stringify([form.formData])
    )
      .then(json => {
        persistFormData(currentTab.tabKey, json[0]);
        toastr.success('Данные успешно сохранены');
        // resizeInputs(formInputs);
      })
      .catch(error => {
        doneTab();
        toastr.error('Ошибка при сохранении', error);
      });
  };

  changeData = form => {
    const { currentTab, storeTmpFormData } = this.props;
    storeTmpFormData(currentTab.tabKey, { data: form.formData });
  };

  render () {
    const { currentTab } = this.props;
    const entity = getEntityByType(currentTab.tabKey);
    const mode = currentTab.form.id ? 'edit' : 'add';

    if (!currentTab.form || !currentTab.form.data) {
      return (<div>Nothing here yet...</div>);
    }

    return (
      <Fragment>
        <Form
          schema={entity.schema}
          uiSchema={entity.uiSchema}
          formData={currentTab.form.data}
          autocomplete='off'
          onChange={this.changeData}
          onSubmit={mode === 'edit' ? this.putData : this.postData}
          onError={error => toastr.error('Пожалуйста, проверьте введеные данные', error)}>
          <button disabled={this.state.loading} type='submit' className='record__button'>Сохранить</button>
        </Form>
      </Fragment>
    );
  }

  componentDidMount () {
    const { currentTab } = this.props;
    const mode = currentTab.form.id ? 'edit' : 'add';
    
    if (mode === 'edit') {
      this.getData();
    }

    // resizeInputs(formInputs);
  }

  componentDidUpdate () {
    
  }
}

const mapDispatchToProps = dispatch => {
  return {
    loadingTab: () => {
      dispatch(loadingTab());
    },
    doneTab: () => {
      dispatch(doneTab());
    },
    setFormData: (tabKey, payload) => {
      dispatch(setTabFormData(tabKey, payload));
    },
    persistFormData: (tabKey, payload) => {
      dispatch(persistTabFormData(tabKey, payload));
    },
    storeTmpFormData: (tabKey, payload) => {
      dispatch(storeTabTmpFormData(tabKey, payload));
    }
  };
};

export default connect(null, mapDispatchToProps)(RecordEditor);