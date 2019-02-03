import { Search } from 'semantic-ui-react';
import React, { Component } from 'react';
import ajaxRequest from '../../../../../helpers/ajaxRequest';
import SearchFieldResults from '../SearchFieldResults';
import { toastr } from 'react-redux-toastr';

const WAIT_INTERVAL = 1000;
const ENTER_KEY = 13;

export default class RenderSearchField extends Component {
  state = {
    isLoading: false,
    results: [],
    value: '',
    hiddenValue: ''
  };

  componentWillMount () {
    this.resetComponent();
    this.timer = null;
  }

  resetComponent = () => this.setState({ isLoading: false, results: [], value: '' });

  loadFieldValue = () => {
    const { entity, entityId } = this.props;

    if (entityId) {
      ajaxRequest.get('/' + entity + '/' + entityId)
        .then(result => {
          let title = result.data.title;
          
          if (entity === 'clients') {
            title = result.data.firstName + ' ' + result.data.lastName;
          }

          this.setState({
            value: title
          });
        })
        .catch(error => toastr.error(error.message));
    }
  }

  handleSearchChange = (e, { value }) => {
    clearTimeout(this.timer);
    this.setState({ value });
    this.timer = setTimeout(this.triggerChange, WAIT_INTERVAL);
  }

  handleKeyDown = (e) => {
    if (e.keyCode === ENTER_KEY) {
      clearTimeout(this.timer);
      this.triggerChange();
    }
  }

  triggerChange = () => {
    const { entity } = this.props;
    const { value } = this.state;

    this.setState({
      isLoading: true,
      value,
      hiddenValue: ''
    });

    ajaxRequest.get('/' + entity + '/?search=' + value).then(searchResults => {
      if (this.state.value.length < 1) return this.resetComponent();
      const results = searchResults.data.map(result => {
        let title = result.title;
        
        if (entity === 'clients') {
          title = result.firstName + ' ' + result.lastName;
        }

        return {
          id: result.id,
          title
        };
      });

      this.setState({
        isLoading: false,
        results: results
      });
    });
  }

  handleResultSelect = (e, { result }) => {
    const fieldName = this.props.input.name;
    const { changeField } = this.props;
    changeField(fieldName, result.id);
    this.setState({ value: result.title });
  }

  unassignEntity = (fieldName) => {
    const { changeField } = this.props;
    changeField(fieldName, -1);

    this.setState({
      value: '',
      hiddenValue: ''
    });
  }

  onFocus = () => {
    if (this.state.value !== '') {
      const hiddenValue = this.state.value;

      this.setState({
        value: '',
        hiddenValue
      });
    }
  }

  onBlur = () => {
    if (this.state.hiddenValue !== '') {
      const hiddenValue = this.state.hiddenValue;

      this.setState({
        value: hiddenValue,
        hiddenValue: ''
      });
    }
  }

  render () {
    const { isLoading, value, results } = this.state;
    let {
      input,
      label,
      meta: { touched, error, warning }
    } = this.props;

    return (
      <div className="form-group field field-string">
        <label className="control-label">{label}</label>
        <Search
          loading={isLoading}
          onResultSelect={this.handleResultSelect}
          onSearchChange={this.handleSearchChange}
          onKeyDown={this.handleKeyDown}
          results={results}
          value={value}
          resultRenderer={SearchFieldResults}
          onFocus={() => this.onFocus()}
          onBlur={() => this.onBlur()}
          className={error ? 'field_error-input' : null || warning ? 'field_warning-input' : null}
        />
        <input {...input} type="hidden" />
        {touched &&
          ((error && <div className="field_error-ch">{error}</div>) ||
            (warning && <div className="field_warning-ch">{warning}</div>))}
        <button type="button" className="record__button" onClick={() => this.unassignEntity(input.name)}>Отвязать</button>
      </div>
    );
  }

  componentDidMount () {
    this.loadFieldValue();
  }
}