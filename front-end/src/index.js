import React, {Fragment} from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import * as serviceWorker from './serviceWorker';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Login from './components/Login';
import {Provider} from 'react-redux';
import store from './store';
import ReduxToastr from 'react-redux-toastr';
import './reset.scss';
import './index.scss';
import 'react-redux-toastr/lib/css/react-redux-toastr.min.css';
import 'semantic-ui-css/semantic.min.css';

ReactDOM.render(
  <Provider store={store}>
    <Fragment>
      <BrowserRouter>
        <Fragment>
          <Switch>
            <Route path="/login" component={Login}/>
            <Route path="/" component={App}/>
          </Switch>
        </Fragment>
      </BrowserRouter>
      <ReduxToastr
        timeOut={1500}
        newestOnTop={false}
        preventDuplicates
        position="top-center"
        transitionIn="fadeIn"
        transitionOut="fadeOut"
        progressBar
        closeOnToastrClick/>
    </Fragment>
  </Provider>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
