import { Search } from 'semantic-ui-react';
import React, { Component } from 'react';
import ajaxRequest from '../../../../../helpers/ajaxRequest';
import SearchFieldResults from '../SearchFieldResults';
import { toastr } from 'react-redux-toastr';

export default class RenderSearchField extends Component {
  state = {
    isLoading: false,
    results: [],
    value: '',
    hiddenValue: ''
  };

  componentWillMount () {
    this.resetComponent();
  }

  resetComponent = () => this.setState({ isLoading: false, results: [], value: '' });

  loadFieldValue = () => {
    const { childEntity, childId } = this.props;

    ajaxRequest.get('/' + childEntity + '/' + childId)
      .then(result => {
        let title = result.data.title;
        
        if (childEntity === 'clients') {
          title = result.data.firstName + ' ' + result.data.lastName;
        }

        this.setState({
          value: title
        });
      })
      .catch(error => toastr.error(error.message));
  }

  saveFieldValue = (e) => {
    const { parentEntity, parentId, childEntity, childId } = this.props;
    
    ajaxRequest.put('/' + parentEntity + '/' + parentId + '/' + childEntity + '/' + childId)
      .then(result => toastr.success('Данные успешно сохранены'))
      .catch(error => toastr.error(error.message));
  }

  handleSearchChange = (e, { value }) => {
    const { childEntity } = this.props;

    this.setState({
      isLoading: true,
      value,
      hiddenValue: ''
    });

    ajaxRequest.get('/' + childEntity + '/?search=' + value).then(searchResults => {
      if (this.state.value.length < 1) return this.resetComponent();
      const results = searchResults.data.map(result => {
        let title = result.title;
        
        if (childEntity === 'clients') {
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
    const entityId = this.props.input.name;
    const { changeField } = this.props;
    changeField(entityId, result.id);
    this.setState({ value: result.title });
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
          results={results}
          value={value}
          resultRenderer={SearchFieldResults}
          onFocus={() => this.onFocus()}
          onBlur={() => this.onBlur()}
        />
        <input {...input} type="hidden" />
        <button type="button" className="record__button" onClick={(e) => this.saveFieldValue(e)}>Сохранить</button>
      </div>
    );
  }

  componentDidMount () {
    this.loadFieldValue();
  }
}