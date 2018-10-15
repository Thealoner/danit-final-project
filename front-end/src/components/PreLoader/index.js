import React, {Component} from 'react';
import './index.scss';

class PreLoader extends Component {
    render() {
        return (
            <section className="preloader" ref='preloader'>
                <div className="preloader__wrapper">
                    <h2 className='preloader__title'>Loading</h2>
                    <div className="preloader__lines">
                        <span className="preloader__line preloader__line--one"/>
                        <span className="preloader__line preloader__line--two"/>
                        <span className="preloader__line preloader__line--three"/>
                        <span className="preloader__line preloader__line--four"/>
                        <span className="preloader__line preloader__line--five"/>
                        <span className="preloader__line preloader__line--six"/>
                        <span className="preloader__line preloader__line--seven"/>
                        <span className="preloader__line preloader__line--eight"/>
                        <span className="preloader__line preloader__line--nine"/>
                    </div>
                </div>
            </section>
        );
    }

    componentDidMount() {
        if (this.props.fullScreen) {
            this.refs.preloader.style.width = '100vw';
            this.refs.preloader.style.height = '100vh';
            this.refs.preloader.style.background = '#333'; // test
        }
    }
}

export default PreLoader;