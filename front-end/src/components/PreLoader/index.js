import React, {Component} from 'react';
import './index.scss';

class PreLoader extends Component {
  render () {
    return (
      <section className="preloader">
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
}

export default PreLoader;