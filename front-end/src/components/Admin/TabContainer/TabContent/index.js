import React, {Component} from 'react';
import {setTabGridData} from '../../../../actions/tabActions';
import {connect} from 'react-redux';
import Grid from '../../Grid';
import RecordEditor from '../../Record/RecordEditor';

class TabContent extends Component {
  state = {};

  render () {
    const {currentTab} = this.props;

    if (currentTab.type === 'grid') {
      return (
        <div className="tabs__content">
          <Grid currentTab={currentTab}/>
        </div>
      );
    }

    if (currentTab.type === 'form') {
      return (
        <div className="tabs__content">
          <RecordEditor currentTab={currentTab}/>
        </div>
      );
    }

    return (
      <div className="tabs__content">{JSON.stringify(currentTab)}</div>
    );
  }
}

const mapDispatchToProps = (dispatch) => ({
  setTabGridData: (tabKey, payload) => {
    dispatch(setTabGridData(tabKey, payload));
  }
});

export default connect(null, mapDispatchToProps)(TabContent);