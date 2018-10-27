import React, {Component} from 'react';
import PreLoader from './components/PreLoader';
import './Reset.scss';
import './App.scss';
import Home from './components/Home';
import Menu from './components/Menu';
import Packages from './components/Packages';
import {BrowserRouter as Router, Route} from 'react-router-dom';

class App extends Component {
  render () {
    return (
      <Router>
        <div className="App">
          <div className="App-preloader" ref="preloader"><PreLoader fullScreen={false}/></div>
          <div className="App__leftpanel">
            <Menu />
          </div>
          <div className="App__rightpanel">
            <Route exact path="/" component={Home} />
            <Route path="/packages" component={Packages} />
          </div>
        </div>
      </Router>
    );
  }

  componentDidMount () {
    let self = this;
    setTimeout(function () {
      self.refs.preloader.style.opacity = '0';
      self.refs.preloader.style.visibility = 'hidden';
    }, 2000);
    setTimeout(function () {
      self.refs.preloader.style.display = 'none';
    }, 3000);
  }
}

export default App;
