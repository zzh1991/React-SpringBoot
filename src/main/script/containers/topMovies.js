import React from 'react';
import { Table, Icon } from 'antd';
import { connect } from 'react-redux';
import '../styles/style.css';
import { fetchMovieTop } from '../actions/actions';

class TopMovies extends React.Component {
  state = {
    current: 1,
    index: -1,
  };

  componentDidMount() {
    this.props.dispatch(fetchMovieTop.request())
  };

  onChange = (pagination, filters, sorter) => {
  };

  onRowClick = (record, index, event) => {
    this.setState({ index, });
  };

  rowClassName = (record, index) => {
    if (index === this.state.index) {
      return 'clicked';
    }
    return '';
  };

  render () {

    const columns = [
      {
        title: '电影名称',
        key: 'name',
        dataIndex: 'title',
        render: (text, record) => {
          return <a href={record.url}>{text}</a>
        },
        width: 300,
      },
      {
        title: '豆瓣评分',
        key: 'rate',
        dataIndex: 'rating',
        sorter: (a, b) => a.rating - b.rating,
        width: 100,
      },
      {
        title: '上映年份',
        key: 'year',
        dataIndex: 'movie_year',
        width: 150,
      },
      // {
      //   title: 'Alt',
      //   key: 'alt',
      //   dataIndex: 'alt',
      // }
    ];

    let { data } = this.props;
    data.map((item, index) => {
      item['key'] = index;
    });

    return (
      <div>

        <Table
          columns={columns}
          dataSource={data}
          pagination={{
            pageSize: 10,
            current: this.state.current,
            defaultCurrent: 1,
            total: data ? data.length : 0,
            showSizeChanger: true,
            onShowSizeChange: (current, pageSize) => {
            },
            onChange: (page) => {
              this.setState({
                current: page,
              });
            },
          }}

          // onChange={this.onChange}
          // onRowClick={this.onRowClick}

          // rowClassName={'table-content'}
          expandedRowRender={record => {
            return (
              <div className={'extra-info'}>
                <img src={record.image_large} />
                <div className={'casts-info'}>
                  <h2>{'主演'}</h2>
                  {
                    record.casts.split(',').map((cast) => {
                      return <h3 key={cast} >{cast}</h3>
                    })
                  }
                  <h2>{'导演'}</h2>
                  {
                    record.directors.split(',').map((director) => {
                      return <h3 key={director} >{director}</h3>
                    })
                  }
                </div>
              </div>
            );
          }}
          scroll={{ y: 550 }}
        />
      </div>
    );
  }
};

function mapStateToProps(state) {
  return {
    data: state.info.movieTopList.data,
  }
}

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  }
}


export default connect(mapStateToProps, mapDispatchToProps)(TopMovies);

