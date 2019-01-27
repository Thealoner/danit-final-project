import PropTypes from 'prop-types';
import React from 'react';
import { Label } from 'semantic-ui-react';

const SearchFieldResultsRenderer = ({ title }) => <Label content={title} />;

SearchFieldResultsRenderer.propTypes = {
  title: PropTypes.string
};

export default SearchFieldResultsRenderer;