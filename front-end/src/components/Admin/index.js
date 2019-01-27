import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import './index.scss';
import TabContainer from './TabContainer';
import EntitiesMenu from './EntitiesMenu';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

class Admin extends Component {
  state = {
    isMenuClosed: false
  }

  toggleEntitiesMenu = () => this.setState((prevState) => ({isMenuClosed: !prevState.isMenuClosed}))

  render () {
    const { isMenuClosed } = this.state;

    return (
      <main className={`configurator ${isMenuClosed ? 'closed' : ''}`}>
        <div className="configurator__left">
          <div className={`configurator__menu-wrapper ${isMenuClosed ? '' : 'opened'}`}>
            <div className="configurator__close-panel">
              <FontAwesomeIcon icon="angle-right" size="1x" onClick={this.toggleEntitiesMenu} />
            </div>
            <div className="configurator__menu">
              <EntitiesMenu />
            </div>
          </div>
        </div>
        <div className="configurator__right">
          <TabContainer />
        </div>
      </main>
    );
  }
}

export default withRouter(Admin);