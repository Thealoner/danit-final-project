import React from 'react';
import { withRouter } from 'react-router-dom';
import './index.scss';
import TabContainer from './TabContainer';
import EntitiesMenu from './EntitiesMenu';

const Admin = () => {
  return (
    <main className="configurator">
      <div className="configurator__left">
        <EntitiesMenu />
      </div>
      <div className="configurator__right">
        <TabContainer />
      </div>
    </main>
  );
};

export default withRouter(Admin);