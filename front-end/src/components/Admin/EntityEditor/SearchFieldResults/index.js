import PropTypes from 'prop-types'
import React from 'react'
import { Label } from 'semantic-ui-react'

const SearchFieldResultsRenderer = ({ firstName, lastName }) => <Label content={firstName + ' ' + lastName} />

SearchFieldResultsRenderer.propTypes = {
  title: PropTypes.string,
  description: PropTypes.string,
}
export default SearchFieldResultsRenderer