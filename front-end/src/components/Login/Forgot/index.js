import React, {Component} from 'react';
import './index.scss';
import $ from 'jquery';

class Forgot extends Component{

    componentDidMount = () => {
        $('.forgot__close').on('click', function () {
            $('.forgot').fadeOut();
/*
            $('.login').fadeIn();
*/
        });
    };

    render(){
        return (

            <div className="forgot">
{/*
                <Link to="/forgot" className="forgot">Forgot password?</Link>
*/}
                <div className="forgot__dialog">
                    <span className="forgot__close">&times;</span>
                    <div className="forgot__wrapper">
                        <form action="#" className="forgot__form">
                            <label htmlFor="username">Enter email:</label>
                            <input type="text" name="" id="username" placeholder="E-mail" autoComplete="off"/>
                            {/*<label htmlFor="password">Password</label>
                            <input type="password" name="" id="password" placeholder="password"/>
                            <label htmlFor="password">Confirm Password</label>
                            <input type="confirm_password" name="" id="confirm_password" placeholder="Confirm password"/>
                            <input type="submit" name="" value="Sign Up"/>*/}
                        </form>
                    </div>
                </div>
            </div>
        )
    }
}

export default Forgot