import React, {Component} from 'react';
import './index.scss';
import $ from 'jquery';

class Forgot extends Component {
    componentDidMount = () => {
      let self = this;
      $('.forgot__close').on('click', function () {
        $('.forgot').fadeOut();
      });

      $(window).on('click', function (event) {
        if (event.target === self.refs.forgot) {
          $('.forgot').fadeOut();
        }
      });
    };

    render () {
      return (
        <div className="forgot" ref="forgot">
          <div className="forgot__dialog">
            <span className="forgot__close">&times;</span>
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