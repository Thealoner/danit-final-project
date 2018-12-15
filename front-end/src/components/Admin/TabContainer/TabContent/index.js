import React, { Component } from 'react';
import './index.scss';
import { setTabContent } from '../../../../actions/tabActions';
import { connect } from 'react-redux';
import ajaxRequest from '../../../../helpers/ajaxRequest';

class TabContent extends Component {
  state = {};

  render () {
    const { currentTab } = this.props;

    if (!currentTab) {
      return <div>Nothing here</div>;
    }
    
    return (
      <div className="tab-content">{JSON.stringify(currentTab)}</div>
    );
  }

  componentDidMount () {
    const { currentTab } = this.props;
    
    if (currentTab && currentTab.tabKey) {
      ajaxRequest('/' + currentTab.tabKey)
        .then(response => {
          console.log(response);
          this.props.setTabContent(currentTab.tabKey, {
            type: 'grid',
            data: response.data,
            meta: response.meta,
            status: 'done'
          });
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};

const mapStateToProps = state => {
  let currentTab = null;
  
  if (state.tabs.activeKey && state.tabs.tabsArray.length > 0) {
    currentTab = state.tabs.tabsArray.filter(t => t.tabKey === state.tabs.activeKey)[0];
  }

  return {
    currentTab
  };
};

const mapDispatchToProps = dispatch => {
  return {
    setTabContent: (tabKey, payload) => {
      dispatch(setTabContent(tabKey, payload));
    }
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(TabContent);