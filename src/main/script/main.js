import React from 'react';
import { Table, Icon } from 'antd';

const columns = [{
    title: 'Name',
    dataIndex: 'name',
  sorter: (a, b) => a.name.length - b.name.length,
  width: 100,
}, {
    title: 'Age',
    dataIndex: 'age',
  sorter: (a, b) => a.age - b.age,
  width: 100,
}, {
    title: 'Address',
    dataIndex: 'address',
  sorter: (a, b) => a.address.length - b.address.length,
  width: 300,
}];

const data = [];
for (let i = 0; i < 46; i++) {
    data.push({
        key: i,
        name: `Edward King ${i}`,
        age: 32,
        address: `London, Park Lane no. ${i}`,
    });
}



class Main extends React.Component {
  state = {
    current: 1,
  };

  onChange = (pagination, filters, sorter) => {
    console.log('params', pagination, sorter);
  };

  render () {
    return (
      <div style={{ width: '500px' }}>
          <Table columns={columns} dataSource={data} pagination={{
            pageSize: 12,
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
          }} onChange={this.onChange}
          />
      </div>
    );
  }
};

export default Main;
