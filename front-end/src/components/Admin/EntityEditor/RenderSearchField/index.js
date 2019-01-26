import { Search } from 'semantic-ui-react';
import React, { Component } from 'react';
import ajaxRequest from '../../../../helpers/ajaxRequest';
import SearchFieldResults from '../SearchFieldResults';

export default class RenderSearchField extends Component {
  state = {
    isLoading: false, results: [], value: ''
  };

  componentWillMount() {
    this.resetComponent();
  }

  resetComponent = () => this.setState({ isLoading: false, results: [], value: '' });

  handleResultSelect = (e, { result }) => {
    const { changeField } = this.props;
    changeField('clientId', result.id);
    this.setState({ value: result.firstName + ' ' + result.lastName });
  }

  handleSearchChange = (e, { value }) => {
    this.setState({ isLoading: true, value })

    ajaxRequest.get('/clients?search=' + value).then(searchResults => {
      if (this.state.value.length < 1) return this.resetComponent();

      this.setState({
        isLoading: false,
        results: searchResults.data
      });
    });
  }

  render() {
    const { isLoading, value, results } = this.state;
    const { input,
      label,
      type,
      name,
      meta: { touched, error, warning }
    } = this.props;

    return (
      <>
        <Search
          loading={isLoading}
          onResultSelect={this.handleResultSelect}
          onSearchChange={this.handleSearchChange}
          results={results}
          value={value}
          resultRenderer={SearchFieldResults}
          // {...this.props}
        />
        <input {...input} type="hidden" />
      </>
    );
  }
}