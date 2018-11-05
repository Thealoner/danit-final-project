import React, { Component } from 'react';
import { NavLink, withRouter } from 'react-router-dom';
import './index.scss';
import GridEntities from './GridEntities';
import Tab from './Tab';

let index = 1;

class Main extends Component {
  state = {
    tabs: [{
      title: 'Title',
      content: 'Content',
      tabId: '',
      activeModule: ''
    }],
    activeKey: 'Title',
    links: []
  };

  constructor (props) {
    super(props);

    GridEntities.forEach((entity) => {
      this.state.links.push(
        <li key={entity.id} className="main__menuitem">
          <NavLink exact to={'/admin/' + this.state.activeKey + '/' + entity.id} activeClassName="menu__link--active" className="menu__link">{entity.name}</NavLink>
        </li>
      );
    });
  }
  

  add = (e) => {
    e.stopPropagation();
    index++;
    const newTab = {
      title: `Title: ${index}`,
      content: `Content: ${index}`,
      tabId: `${index}`,
      activeModule: ''
    };
    this.setState({
      tabs: this.state.tabs.concat(newTab),
      activeKey: `ActiveKey: ${index}`,
    });
  };

  onTabChange = (activeKey) => {
    this.setState({
      activeKey
    });

    let clickedTab = this.state.tabs.find((tab) => {
      return tab.title === activeKey;
    })
    this.props.history.push('/admin/'  + activeKey + '/' + clickedTab.activeModule);
  };

  remove = (title, e) => {
    e.stopPropagation();
    if (this.state.tabs.length === 1) {
      alert('Error. You cannot delete this tab');
      return;
    }
    let foundIndex = 0;
    const after = this.state.tabs.filter((t, i) => {
      if (t.title !== title) {
        return true;
      }
      foundIndex = i;
      return false;
    });

    let activeKey = this.state.activeKey;
    if (activeKey === title) {
      if (foundIndex) {
        foundIndex--;
      }
      activeKey = after[foundIndex].title;
    }
    this.setState({
      tabs: after,
      activeKey
    });
  };

  setActiveModule = (module) => {
    let currenTab = this.state.tabs.find((tab) => {
      return tab.title === this.state.activeKey;
    });

    currenTab.activeModule = module;
  }

  render () {
    return (

      <main className="main">
        <div className="main__left">
          <ul className="main__menu">
            {this.state.links}
          </ul>
        </div>
        <div className="main__right">
          <Tab
            className="tabs"
            add={this.add}
            onTabChange={this.onTabChange}
            remove={this.remove}
            activeKey={this.state.activeKey}
            tabs={this.state.tabs}
            setActiveModule={this.setActiveModule}
          />
        </div>
      </main>
    );
  }
}

export default withRouter(Main);