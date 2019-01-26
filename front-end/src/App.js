import React from 'react';
import {Route, Switch} from 'react-router-dom';
import HomePage from './components/HomePage';
import LoginPage from './components/LoginPage';
import ResetPassword from './components/ResetPassword';
import UnauthenticatedRoute from './components/LoginPage/UnauthenticatedRoute';

const App = (childProps) =>
  <Switch>
    <Route path="/login/reset" component={ResetPassword}/>
    <UnauthenticatedRoute
      path="/login/reset"
      exact
      component={ResetPassword}
      props={childProps}
    />
    <Route path="/login" component={LoginPage}/>
    <Route path="/" component={HomePage}/>
  </Switch>;

export default App;
