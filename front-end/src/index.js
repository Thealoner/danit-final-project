import React, {Fragment} from 'react';
import ReactDOM from 'react-dom';
import './index.scss';
import './Reset.scss';
import App from './App';
import * as serviceWorker from './serviceWorker';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Login from './components/Login';
import { addLocaleData } from 'react-intl';
import en from 'react-intl/locale-data/en';
import ru from 'react-intl/locale-data/ru';
import {Provider} from 'react-redux';
import store from './store';

addLocaleData(en);
addLocaleData(ru);

ReactDOM.render(
  <Provider store={store}>
    <BrowserRouter>
      <Fragment>
        <Switch>
          <Route path="/login" component={Login} />
          <Route path="/" component={App} />
        </Switch>
      </Fragment>
    </BrowserRouter>
  </Provider>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
