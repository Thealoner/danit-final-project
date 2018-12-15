import React, { Component } from 'react';
import { openTab, setTabContent } from '../../../actions/tabActions';
import { connect } from 'react-redux';
import ajaxRequest from '../../../helpers/ajaxRequest';
import TabPane from './TabPane';
import TabContent from './TabContent';

class TabContainer extends Component {
  render () {
    return (
      <div className="tab-container">
        <TabPane
          tabs={this.props.tabs}
          onSelect={this.props.openTab}
        />
        <TabContent content={this.props.tabs.activeKey} />
      </div>
    );
  }

  componentDidUpdate () {
    const { activeKey } = this.props.tabs;
    
    // ajaxRequest('/' + activeKey)
    //   .then(response => {
    //     console.log(response);
    //     this.props.setTabContent(activeKey, {
    //       type: 'grid',
    //       data: response.data,
    //       meta: response.meta,
    //       status: 'loaded'
    //     });
    //   })
    //   .catch(error => {
    //     console.log(error);
    //   });
  }
}

const mapStateToProps = state => {
  return {
    tabs: state.tabs
  };
};

const mapDispatchToProps = dispatch => {
  return {
    openTab: tabKey => {
      dispatch(openTab(tabKey));
    },
    setTabContent: (tabKey, payload) => {
      dispatch(setTabContent(tabKey, payload));
    }
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(TabContainer);