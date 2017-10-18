import React from 'react';
import { Table, Icon } from 'antd';
import { connect } from 'react-redux';
import Checkbox from 'material-ui/Checkbox';
import '../styles/style.css';
import { fetchMovieViewed } from '../actions/actions';

const movieType = [
  {
    text: '剧情',
    value: '剧情',
  },
  {
    text: '动作',
    value: '动作',
  },
  {
    text: '喜剧',
    value: '喜剧',
  },
  {
    text: '爱情',
    value: '爱情',
  },
  {
    text: '战争',
    value: '战争',
  },
  {
    text: '纪录片',
    value: '纪录片',
  },
  {
    text: '科幻',
    value: '科幻',
  },
  {
    text: '动画',
    value: '动画',
  },
];

class Main extends React.Component {
  state = {
    current: 1,
    index: -1,
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

  updateCheck = (event, isChecked, id) => {
    this.props.dispatch(fetchMovieViewed.request({
      id,
      viewed: isChecked,
    }));
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
        width: 200,
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
        sorter: (a, b) => a.movie_year - b.movie_year,
        width: 150,
      },
      {
        title: '类型',
        key: 'genres',
        dataIndex: 'genres',
        width: 150,
        filters: movieType,
        filterMultiple: false,
        onFilter: (value, record) => record.genres.indexOf(value) !== -1,
        filtered: true,
      },
      {
        title: '已观影',
        key: 'viewed',
        dataIndex: 'viewed',
        render: (text, record) => {
          return <Checkbox
            defaultChecked={text}
            onCheck={(event, isChecked) => {this.updateCheck(event, isChecked, record.movieid) }}
          />;
        },
        width: 100,
        filters: [
          {
            text: '已观影',
            value: true
          },
          {
            text: '未观影',
            value: false
          },
        ],
        filterMultiple: false,
        onFilter: (value, record) => record.viewed,
      },
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
                   <hr/>
                   <h2>{'简介'}</h2>
                   <h3>{record.summary}</h3>
                   <h2>{'国家'}</h2>
                   <h3>{record.countries}</h3>
                 </div>
               </div>
             );
           }}
           scroll={{ y: '65vh' }}
          />
      </div>
    );
  }
};

function mapStateToProps(state) {
  return {
    // data: state.info.movieRecentList.data,
  }
}

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  }
}


export default connect(mapStateToProps, mapDispatchToProps)(Main);

