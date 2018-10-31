import React, {Component} from 'react';
import './index.scss';
import {Route} from 'react-router-dom';
import Clients from './Clients';
import Category1 from './Clients/Category1';
import Category2 from './Clients/Category2';
import Category3 from './Clients/Category3';
import Category4 from './Clients/Category4';
import Category5 from './Clients/Category5';
import Packages from './Packages';
import Package1 from './Packages/Package1';
import Package2 from './Packages/Package2';
import Package3 from './Packages/Package3';
import Package4 from './Packages/Package4';
import Package5 from './Packages/Package5';
import Contracts from './Contracts';
import Contract1 from './Contracts/Contract1';
import Contract2 from './Contracts/Contract2';
import Contract3 from './Contracts/Contract3';
import Services from './Services';
import Service1 from './Services/Service1';
import Service2 from './Services/Service2';
import Service3 from './Services/Service3';
import Organizations from './Organizations';
import Organization1 from './Organizations/Organization1';
import Organization2 from './Organizations/Organization2';

class Configurator extends Component {
  render () {
    return (
      <div className="configurator">
        <div className="configurator__left">
          <Route path="/configurator/packages" component={Packages}/>
          <Route path="/configurator/clients" component={Clients}/>
          <Route path="/configurator/contracts" component={Contracts}/>
          <Route path="/configurator/services" component={Services}/>
          <Route path="/configurator/organizations" component={Organizations}/>
        </div>
        <div className="configurator__right">
          <Route exact path="/configurator/packages/package1" component={Package1}/>
          <Route exact path="/configurator/packages/package2" component={Package2}/>
          <Route exact path="/configurator/packages/package3" component={Package3}/>
          <Route exact path="/configurator/packages/package4" component={Package4}/>
          <Route exact path="/configurator/packages/package5" component={Package5}/>

          <Route exact path="/configurator/clients/category1" component={Category1}/>
          <Route exact path="/configurator/clients/category2" component={Category2}/>
          <Route exact path="/configurator/clients/category3" component={Category3}/>
          <Route exact path="/configurator/clients/category4" component={Category4}/>
          <Route exact path="/configurator/clients/category5" component={Category5}/>

          <Route exact path="/configurator/contracts/contract1" component={Contract1}/>
          <Route exact path="/configurator/contracts/contract2" component={Contract2}/>
          <Route exact path="/configurator/contracts/contract3" component={Contract3}/>

          <Route exact path="/configurator/services/service1" component={Service1}/>
          <Route exact path="/configurator/services/service2" component={Service2}/>
          <Route exact path="/configurator/services/service3" component={Service3}/>

          <Route exact path="/configurator/organizations/organization1" component={Organization1}/>
          <Route exact path="/configurator/organizations/organization2" component={Organization2}/>
        </div>
      </div>
    );
  }
}

export default Configurator;