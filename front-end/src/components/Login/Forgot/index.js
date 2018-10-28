import React, {Component} from 'react';
import './index.scss';
import $ from 'jquery';

class Forgot extends Component {
    componentDidMount = () => {
      $('.forgot__close').on('click', function () {
        $('.forgot').fadeOut();
      });
    };

    render () {
      return (
        <div className="forgot">
          <div className="forgot__dialog">
            <span className="forgot__close">&times;</span>
            <div className="forgot__wrapper">
              <form action="#" className="forgot__form">
                <label htmlFor="username">Enter email:</label>
                <input type="text" name="" id="username" placeholder="E-mail" autoComplete="off"/>
                <input type="submit" name="" value="Send"/>
              </form>
            </div>
          </div>
        </div>
      );
    }
}

export default Forgot;