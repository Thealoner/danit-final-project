import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Glyphicon } from 'react-bootstrap';
import AuthService from '../../helpers/authService';
import './index.scss';
import {Form, Button} from 'semantic-ui-react';
import {toastr} from 'react-redux-toastr';

export default class ResetPassword extends Component {
  constructor (props) {
    super(props);
    this.auth = new AuthService();

    this.state = {
      code: '',
      email: '',
      password: '',
      codeSent: false,
      confirmed: false,
      confirmPassword: '',
      isConfirming: false,
      isSendingCode: false
    };
  }

  validateCodeForm () {
    return this.state.email.length > 0;
  }

  validateResetForm () {
    return (
      this.state.code.length > 0 &&
      this.state.password.length > 0 &&
      this.state.password === this.state.confirmPassword
    );
  }

  handleChange = event => {
    this.setState({
      [event.target.id]: event.target.value
    });
  };

  handleSendCodeClick = async event => {
    event.preventDefault();

    this.setState({ isSendingCode: true });

    try {
      await this.auth.requestResetToken(this.state.email);
      this.setState({ codeSent: true });
    } catch (e) {
      toastr.error('Введен неверный email, попробуйте еще раз.');
      this.setState({ isSendingCode: false });
    }
  };

  handleConfirmClick = async event => {
    event.preventDefault();

    this.setState({ isConfirming: true });

    try {
      await this.auth.requestPasswordUpdate(
        this.state.code,
        this.state.password
      );
      this.setState({ confirmed: true });
    } catch (e) {
      toastr.error('Введен неверный код подтверждения или пароли не совпадают, попробуйте еще раз.');
      this.setState({ isConfirming: false });
    }
  };

  renderRequestCodeForm () {
    return (
      <div className='send-email'>
        <div className='send-email__wrapper'>
          <Form onSubmit={this.handleSendCodeClick} className='send-email__form '>
            <Form.Field>
              <label htmlFor='email'>Email</label>
              <input type="email"
                id='email'
                name="email"
                value={this.state.email}
                onChange={this.handleChange}/>
            </Form.Field>
            <Button onClick={ this.state.isSendingCode} disabled={!this.validateCodeForm()}>Send Confirmation</Button>
          </Form>
        </div>
      </div>
    );
  }

  renderConfirmationForm () {
    return (
      <div className="send-email">
        <div className="send-email__wrapper">
          <Form onSubmit={this.handleConfirmClick} className='send-email__form '>
            <Form.Field>
              <label>Confirmation Code</label>
              <input
                type="text"
                id='code'
                name="text"
                value={this.state.code}
                onChange={this.handleChange}
              />
              <label>
                Please check your email ({this.state.email}) for the confirmation
                code.
              </label>
            </Form.Field>
            <Form.Field>
              <label>New Password</label>
              <input
                type="password"
                id="password"
                name="password"
                value={this.state.password}
                onChange={this.handleChange}
              />
            </Form.Field>
            <Form.Field>
              <label>Confirm Password</label>
              <input
                type="password"
                id="confirmPassword"
                name="confirmPassword"
                value={this.state.confirmPassword}
                onChange={this.handleChange}
              />
            </Form.Field>
            <Button onClick={this.state.isConfirming} disabled={!this.validateResetForm()}>Confirm</Button>
          </Form>
        </div>
      </div>
    );
  }

  renderSuccessMessage () {
    return (
      <div className="success">
        <Glyphicon glyph="ok" />
        <p>Your password has been reset.</p>
        <p>
          <Link to="/login">
            Click here to login with your new credentials.
          </Link>
        </p>
      </div>
    );
  }

  render () {
    return (
      <div className="ResetPassword">
        {!this.state.codeSent
          ? this.renderRequestCodeForm()
          : !this.state.confirmed
            ? this.renderConfirmationForm()
            : this.renderSuccessMessage()}
      </div>
    );
  }
}
