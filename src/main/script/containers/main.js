import React from 'react';
import { Table, Icon } from 'antd';
import { connect } from 'react-redux';
import '../styles/style.css';
import { fetchData } from '../actions/actions';
import { fetchStudentData } from '../actions/actions';
import { requestStudent, fetchStudentInfoRequest } from '../actions/miniAction';

class Main extends React.Component {
  state = {
    current: 1,
    index: -1,
  };

  componentDidMount = () => {
      // this.props.dispatch(fetchData());
    this.props.dispatch(requestStudent());
  };

  onChange = (pagination, filters, sorter) => {
    // console.log('params', pagination, sorter);
  };

  onRowClick = (record, index, event) => {
    // console.log(index);
    // console.log(JSON.stringify(record));
    this.setState({ index, });
    this.props.dispatch(fetchStudentInfoRequest(record.id));
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
      title: 'Id',
      key: 'id',
      dataIndex: 'id',
      // sorter: (a, b) => a.name < b.name,
    }, {
      title: 'Name',
      key: 'name',
      dataIndex: 'name',
      // sorter: (a, b) => a.age - b.age,
    }];

    const { data } = this.props;

    return (
      <div>
          <Table columns={columns} dataSource={data} pagination={{
            pageSize: 2,
            current: this.state.current,
            defaultCurrent: 1,
            total: data ? data.length : 0,
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

function mapStateToProps(state) {
  return {
    data: state.data,
  }
}

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  }
}


export default connect(mapStateToProps, mapDispatchToProps)(Main);

