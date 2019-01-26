import React, { Component } from 'react';
import './profile.scss';
import { Button, Form } from 'semantic-ui-react';
import { toastr } from 'react-redux-toastr';
import ajaxRequest from '../../helpers/ajaxRequest';
import AuthService from '../../helpers/authService';

class Profile extends Component {
  auth = new AuthService();

  state = {
    currentPwd: '',
    newPwd: '',
    repeatPwd: '',
    binaryData: []
  };

  getAvatar = () => {
    ajaxRequest.get('api/storage/avatar', 'storage')
      .then(data => {
        this.setState({
          binaryData: data
        });
      })
      .catch(() => {
        toastr.error('Cant get avatar image');
      });
  };

  handleChg = (event) => {
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  handleChgPwd = () => {
    if (this.state.newPwd === this.state.repeatPwd) {
      this.auth.requestPasswordChange(this.state.currentPwd, this.state.newPwd)
        .then(() => {
          toastr.success('Пароль успешно изменен!');
          this.auth.logout();
        })
        .catch(error => {
          error.response.json().then(data => toastr.error(data.message));
        });
    } else {
      toastr.error('Введенные пароли не совпадают');
    }
  };

  handleUploadImage = (event) => {
    event.preventDefault();
    const data = new FormData();
    data.append('file', this.uploadInput.files[0]);
    ajaxRequest.post('/api/storage/avatar/upload', data, 'storage')
      .then(() => {
        this.getAvatar();
        toastr.success('Аватар успешно изменен!');
      })
      .catch(error => {
        error.response.json().then(data => toastr.error(data.message));
      });
  };

  componentDidMount () {
    this.getAvatar();
  }

  render () {
    return <div className="profile">
      <div className="user-photo">
        <img src={`data:image/png;base64,${this.state.binaryData}`} alt="user-avatar" className="user-avatar"/>
        <Form>
          <Form.Field>
            <input ref={(ref) => { this.uploadInput = ref; }} type='file' className='user-doc'/>
          </Form.Field>
          <Button onClick={this.handleUploadImage}>Загрузить</Button>
        </Form>

      </div>

      <Form onSubmit={this.handleChgPwd} className='profile__form'>

        <Form.Field>
          <label htmlFor='password'>Текущий пароль</label>
          <input type="password"
            id='currentPwd'
            name="currentPwd"
            value={this.state.currentPwd}
            onChange={this.handleChg}/>
        </Form.Field>

        <Form.Field>
          <label htmlFor='password'>Новый пароль</label>
          <input type="password"
            id='newPwd'
            name="newPwd"
            value={this.state.newPwd}
            onChange={this.handleChg}/>
        </Form.Field>

        <Form.Field>
          <label htmlFor='password'>Повторить новый пароль</label>
          <input type="password"
            id='repeatPwd'
            name="repeatPwd"
            value={this.state.repeatPwd}
            onChange={this.handleChg}/>
        </Form.Field>
        <Button onClick={this.handleChgPwd}>Изменить пароль</Button>
      </Form>
    </div>;
  }
}

export default Profile;