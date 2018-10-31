import React, {Component, Fragment} from 'react';
import {ReactTabulator} from 'react-tabulator';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';

const columns = [
  {title: 'Name', field: 'name', width: 150},
  {title: 'BirthDate', field: 'birthDate', align: 'left'},
  {title: 'PhoneNumber', field: 'phoneNumber'},
  {title: 'CardId', field: 'cardId', align: 'center'}
];
const data = [
  {id: 1, name: 'Dmitriy Novikov', birthDate: '18.07.1987', phoneNumber: '+380956358965', cardId: '265987456'},
  {id: 2, name: 'Alexey Fateev', birthDate: '13.04.1984', phoneNumber: '+380638954782', cardId: '852410003'},
  {id: 3, name: 'Serhii Vakulenko', birthDate: '01.01.1999', phoneNumber: '+380508961200', cardId: '596001478'},
  {id: 4, name: 'Serhii Harmash', birthDate: '12.02.1998', phoneNumber: '+380448907685', cardId: '768900112'}
];

class Category1 extends Component {
    state = {
      data: []
    };
    ref = null;

    rowClick = (e, row) => {
      console.log('ref table: ', this.ref.table); // this is the Tabulator table instance
      console.log('rowClick id:' + row.getData().id, row, e);
    };
    setData = () => {
      this.setState({data});
    };
    clearData = () => {
      this.setState({data: []});
    };

    render () {
      const options = {
        height: 150,
        movableRows: true
      };

      return (
        <Fragment>
          <ReactTabulator
            ref={ref => (this.ref = ref)}
            columns={columns}
            data={data}
            rowClick={this.rowClick}
            options={options}
            data-custom-attr="test-custom-attribute"
            className="custom-css-class"
          />
          <h3>Asynchronous data: (e.g. fetch) - <button onClick={this.setData}>Set Data</button>{' '}
            <button onClick={this.clearData}>Clear</button>
          </h3>
          <ReactTabulator columns={columns} data={this.state.data}/>
          <ReactTabulator columns={columns} data={this.state.data}/>
          <ReactTabulator columns={columns} data={this.state.data}/>
          <ReactTabulator columns={columns} data={this.state.data}/>
          <ReactTabulator columns={columns} data={this.state.data}/>
          <ReactTabulator columns={columns} data={this.state.data}/>
          <ReactTabulator columns={columns} data={this.state.data}/>
          <ReactTabulator columns={columns} data={this.state.data}/>
        </Fragment>
      );
    }
}

export default Category1;