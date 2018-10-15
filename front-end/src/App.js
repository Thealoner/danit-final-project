import React, {Component, Fragment} from 'react';
import PreLoader from './components/PreLoader';
import logo from './logo.svg';
import './App.scss';

class App extends Component {

    render() {
        return (
            <Fragment>
                <div className="App-preloader" ref="preloader"><PreLoader fullScreen={false}/></div>
                <div className="App">
                    <header className="App-header">
                        <img src={logo} className="App-logo" alt="logo"/>
                        <p>Edit <code>src/App.js</code> and save to reload.</p>
                        <a className="App-link" href="https://reactjs.org" target="_blank" rel="noopener noreferrer">
                            Learn React
                        </a>
                    </header>
                </div>
            </Fragment>
        );
    }

    componentDidMount() {
        let self = this;
        setTimeout(function () {
            self.refs.preloader.style.opacity = '0';
            self.refs.preloader.style.visibility = 'hidden'
        }, 2000);
        setTimeout(function () {
            self.refs.preloader.style.display = 'none';
        }, 3000)
    }
}

export default App;
