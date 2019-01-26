import React, { Component } from 'react';
import { Loader } from 'semantic-ui-react';
import GridFilter from './GridFilter';
import GridFooter from './GridFooter';
import GridTable from './GridTable';
import 'react-tabulator/lib/styles.css';
import './index.scss';

class Grid extends Component {
  render () {
    const { currentTab } = this.props;

    return (
      <div className="grid">
        <GridFilter currentTab={currentTab}/>
        {currentTab.gridStatus === 'loading'
          ? <div className="tabs__loader-wrapper"><Loader active inline='centered' size='big'/></div>
          : <GridTable currentTab={currentTab}/>
        }
        <GridFooter currentTab={currentTab}/>
      </div>
    );
  }
}

export default Grid;