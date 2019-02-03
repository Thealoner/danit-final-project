import React, {Component} from 'react';
import { Link } from 'react-router-dom';
import './index.scss';
import AuthService from '../../helpers/authService';
import {toastr} from 'react-redux-toastr';
import {Form, Button} from 'semantic-ui-react';

class Login extends Component {
  constructor (props) {
    super(props);
    this.auth = new AuthService();

    this.state = {
      data: {
        username: '',
        password: ''
      },
      loading: false,
      error: false
    };
  }

  handleChange = (e) => this.setState({
    data: {...this.state.data, [e.target.name]: e.target.value}
  });

  handleSubmit = (e) => {
    e.preventDefault();
    const {data} = this.state;

    this.setState({loading: true});

    this.auth.login(data.username, data.password)
      .then(res => {
        this.props.history.replace('/');
      })
      .catch(() => {
        toastr.error('Неверный логин или пароль');
        this.setState({
          loading: false,
          error: true
        });
      });
  };

  render () {
    const {data, error, loading} = this.state;

    return (
      <div className='login'>
        <div className="login__wrapper">
          <Form onSubmit={this.handleSubmit} className='login__form' loading={loading}>
            <Form.Field error={error}>
              <label htmlFor='username'>Логин</label>
              <input type='text'
                id='username'
                name='username'
                placeholder='Введите логин'
                value={data.username}
                onChange={this.handleChange}
                required/>
            </Form.Field>
            <Form.Field error={error}>
              <label htmlFor='password'>Пароль</label>
              <input type='password'
                id='password'
                name='password'
                placeholder='Введите пароль'
                value={data.password}
                onChange={this.handleChange}
                required/>
            </Form.Field>
            <Button>Войти</Button>
          </Form>
          <Link to="/login/reset">Forgot password?</Link>
        </div>
      </div>
    );
  };
}

export default Login;