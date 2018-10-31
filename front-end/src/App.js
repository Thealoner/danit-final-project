import React, {Component, Fragment} from 'react';
import PreLoader from './components/PreLoader';
import './App.scss';
import Header from './components/Header';
import Main from './components/Main';
import Footer from './components/Footer';
import Login from './components/Login';
import Forgot from './components/Login/Forgot';
import Registration from './components/Login/Registration';

class App extends Component {
  render () {
    return (
      <Fragment>
        <div className="preloader" ref="preloader"><PreLoader/></div>
        <div className="app">
          <Header/>
          <Main/>
          <Footer/>
          <Login/>
          <Forgot/> 
          <Registration/>
        </div>
      </Fragment>
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
