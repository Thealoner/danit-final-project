import React, {Fragment} from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import * as serviceWorker from './serviceWorker';
import {BrowserRouter} from 'react-router-dom';
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
        <App/>
      </BrowserRouter>
      <ReduxToastr
        timeOut={1500}
        newestOnTop={false}
        preventDuplicates
        position="top-center"
        transitionIn="fadeIn"
        transitionOut="fadeOut"
        closeOnToastrClick/>
    </Fragment>
  </Provider>,
  document.getElementById('root')
);

serviceWorker.unregister();
