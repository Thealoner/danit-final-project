import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import './index.scss';
import TabContainer from './TabContainer';
import EntitiesMenu from './EntitiesMenu';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

class Admin extends Component {
  configurator = React.createRef();

  hideConfiguratorMenu = () => this.configurator.current.classList.toggle('configurator__menu-hide');

  render () {
    return (
      <main className="configurator" ref={this.configurator}>
        <div className="configurator__left">
          <div className="configurator__menu-wrapper">
            <div className="configurator__close-panel">
              <FontAwesomeIcon icon="angle-right" size="1x" onClick={() => this.hideConfiguratorMenu()} />
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