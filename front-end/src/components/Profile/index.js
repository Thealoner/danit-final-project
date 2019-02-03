import React, { Component } from 'react';
import './profile.scss';
import { Button, Form } from 'semantic-ui-react';
import { toastr } from 'react-redux-toastr';
import AuthService from '../../helpers/authService';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import { getAvatar, postAvatar, deleteAvatar } from '../../actions/userActions';
import defaultAvatar from './default-avatar.png';

class Profile extends Component {
  constructor (props) {
    super(props);
    this.uploadInput = React.createRef();
    this.auth = new AuthService();
    this.state = {
      currentPwd: '',
      newPwd: '',
      repeatPwd: '',
      isFileSelected: false
    };
  }

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
          this.props.history.push('/login');
        })
        .catch(error => {
          error.response.json().then(data => toastr.error(data.message));
        });
    } else {
      toastr.error('Введенные пароли не совпадают');
    }
  };

  handleUploadImage = event => {
    event.preventDefault();
    const data = new FormData();
    data.append('file', this.uploadInput.current.files[0]);
    this.props.postAvatar(data);
  };

  componentDidMount () {
    this.props.getAvatar();
  }

  render () {
    const { user } = this.props;
    
    return <div className="profile">
      <div className="user-photo">
        <img src={user.avatar ? 'data:image/png;base64,' + user.avatar : defaultAvatar} alt="user-avatar" className="user-avatar"/>
        <Form>
          <Form.Field>
            <input
              ref={this.uploadInput}
              type='file'
              className='user-doc'
              onChange={(e) => {
                const { target } = e;
                if (target.value.length > 0) {
                  this.setState({
                    isFileSelected: true
                  });
                } else {
                  this.setState({
                    isFileSelected: false
                  });
                }
              }}
            />
          </Form.Field>
          <Button onClick={this.handleUploadImage} disabled={!this.state.isFileSelected}>Загрузить</Button>
          <Button onClick={this.props.deleteAvatar} disabled={!user.avatar}>Удалить</Button>
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

const mapStateToProps = state => {
  return {
    user: state.user
  };
};

export default withRouter(connect(mapStateToProps, { getAvatar, postAvatar, deleteAvatar })(Profile));