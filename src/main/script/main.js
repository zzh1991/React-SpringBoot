import React from 'react';
import { Table, Icon } from 'antd';
import style from './style.css';






class Main extends React.Component {
  state = {
    current: 1,
    index: -1,
  };

  onChange = (pagination, filters, sorter) => {
    console.log('params', pagination, sorter);
  };

  onRowClick = (record, index, event) => {
    // console.log(index);
    // console.log(JSON.stringify(record));
    this.setState({ index, });
  };

  rowClassName = (record, index) => {
    if (index === this.state.index) {
      return 'clicked';
    }
    return '';
  };

  renderColumn = (text, record, index) => {
    if (record.age === 0) {
      return <span className="failed">{text}</span>
    }
    return <span>{text}</span>
  };

  render () {

    const columns = [{
      title: 'Name',
      dataIndex: 'name',
      // sorter: (a, b) => a.name < b.name,
    }, {
      title: 'Age',
      dataIndex: 'age',
      render: this.renderColumn,
      // sorter: (a, b) => a.age - b.age,
    }, {
      title: 'Address',
      dataIndex: 'address',
      // sorter: (a, b) => a.address < b.address,
    }];

    const data = [];
    for (let i = 0; i < 46; i++) {
      data.push({
        key: i,
        name: `Edward King ${i}`,
        age: i,
        address: `London, Park Lane no. ${i}`,
      });
    }

    return (
      <div>
          <Table columns={columns} dataSource={data} pagination={{
            pageSize: 10,
            current: this.state.current,
            defaultCurrent: 1,
            total: data.length,
            showSizeChanger: true,
            onShowSizeChange: (current, pageSize) => {
              console.log(current, pageSize);
            },
            onChange: (page) => {
              console.log(page);
              this.setState({
                current: page,
              });
            },
          }}
                 onChange={this.onChange}
                 onRowClick={this.onRowClick}
                 rowClassName={this.rowClassName}
          />
      </div>
    );
  }
};

export default Main;
