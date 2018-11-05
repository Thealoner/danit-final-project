import React, {Component} from 'react';
import './index.scss';

class Forgot extends Component {
  render () {
    return (
      <div className="forgot" ref="forgot">
        <div className="forgot__dialog">
          <form action="#" className="forgot__form">
            <label htmlFor="e-mail">Enter your e-mail:</label>
            <input type="text" name="" id="e-mail" placeholder="e-mail"/>
            <input type="submit" name="" value="Send"/>
          </form>
        </div>
      </div>
    );
  }
}

export default Forgot;