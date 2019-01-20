import React from 'react';
import {Route, Switch} from 'react-router-dom';
import HomePage from './components/HomePage';
import LoginPage from './components/LoginPage';
import ResetPassword from './components/ResetPassword';

const App = () =>
  <Switch>
    <Route path="/login" component={LoginPage}/>
    <Route path="/" component={HomePage}/>
    <Route path="/login/reset" component={ResetPassword}/>
  </Switch>;

export default App;
