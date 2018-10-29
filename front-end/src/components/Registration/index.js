import React, {Component} from 'react';
import './index.scss';
import $ from 'jquery';

class Registration extends Component {
    componentDidMount = () => {
        $('.registration__close').on('click', function () {
            $('.registration').fadeOut();
        });
    };

    render () {
        return (
            <div className="registration">
                <div className="registration__dialog">
                    <span className="registration__close">&times;</span>
                    <div className="registration__wrapper">
                        <form action="#" className="registration__form">
                            <label htmlFor="username">Username</label>
                            <input type="text" name="" id="username" placeholder="Username" autoComplete="off"/>
                            <label htmlFor="name">Name</label>
                            <input type="text" name="" id="name" placeholder="Name" autoComplete="off"/>
                            <label htmlFor="password">Password</label>
                            <input type="password" name="" id="password" placeholder="Password"/>
                            <label htmlFor="repeat_password">Repeat Password</label>
                            <input type="password" name="" id="repeat_password" placeholder="Repeat Password"/>
                            <input type="submit" name="" value="Sign up"/>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

export default Registration;