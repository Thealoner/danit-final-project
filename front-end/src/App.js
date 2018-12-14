import React from 'react';
import {Route, Switch} from 'react-router-dom';
import HomePage from './components/HomePage';
import LoginPage from './components/LoginPage';

const App = () =>
  <Switch>
    <Route path="/login" component={LoginPage}/>
    <Route path="/" component={HomePage}/>
  </Switch>;

export default App;
