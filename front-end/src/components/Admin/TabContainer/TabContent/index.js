import React, { Component } from 'react';
import Grid from '../../Grid';
import { Loader } from 'semantic-ui-react';
import RecordEditor from '../../RecordEditor';

class TabContent extends Component {
  render () {
    const { currentTab } = this.props;

    if (currentTab.tabStatus === 'loading') {
      return (
        <div className="tabs__content">
          <div className="tabs__loader-wrapper"><Loader active inline='centered' size='big'/></div>
        </div>
      );
    } else {
      return (
        currentTab.type === 'grid'
          ? <div className="tabs__content">
            <Grid currentTab={currentTab} />
          </div>
          : <div className="tabs__content">
            <RecordEditor currentTab={currentTab} />
          </div>
      );
    }
  }
}

export default TabContent;