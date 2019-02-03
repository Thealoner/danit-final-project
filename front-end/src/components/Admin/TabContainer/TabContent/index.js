import React, { Component } from 'react';
import Grid from '../../Grid';
import { Loader } from 'semantic-ui-react';
import EntityEditor from '../../EntityEditor';

class TabContent extends Component {
  render () {
    const { currentTab } = this.props;
    let content;

    if (currentTab.tabStatus === 'loading') {
      content = <div className="tabs__loader-wrapper"><Loader active inline='centered' size='big'/></div>;
    } else if (currentTab.type === 'grid') {
      content = <Grid currentTab={currentTab} />;
    } else if (currentTab.type === 'form') {
      content = <EntityEditor currentTab={currentTab} />;
    }

    return (
      <div className="tabs__content">
        {content}
      </div>
    );
  }
}

export default TabContent;