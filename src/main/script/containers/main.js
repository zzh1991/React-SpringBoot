import React from 'react';
import { Table, Button, Input } from 'antd';
import { connect } from 'react-redux';
import '../styles/style.css';
import { fetchMovieViewed, saveMovieToLocal, deleteMovieToLocal, getMovieList } from '../actions/actions';
import MovieDetail from '../components/movieDetail';

const Search = Input.Search;

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
const watchedMovieName = 'watchedMovieList';
const starMovieName = 'starMovieList';

class Main extends React.Component {
  state = {
    current: 1,
    pageSize: 6,
    index: -1,
    watchedMovieSet: getMovieList(watchedMovieName),
    starMovieSet: getMovieList(starMovieName),
    searchText: '',
  };

  onShowSizeChange = (current, pageSize) => {
    this.setState({
      current,
      pageSize,
    });
  };

  onChange = (current) => {
    this.setState({
      current,
    });
  };

  updateCheck = (isChecked, id, movieListName) => {
    this.props.dispatch(fetchMovieViewed.request({
      id,
      viewed: isChecked,
    }));

    if (isChecked) {
      saveMovieToLocal(id, movieListName);
      this.setViewOrStarState(movieListName, id);
    } else {
      deleteMovieToLocal(id, movieListName);
      this.setUnviewOrUnstarState(movieListName, id);
    }
  };

  setViewOrStarState = (movieListName, id) => {
    if (movieListName === watchedMovieName) {
      const set = this.state.watchedMovieSet;
      set.add(id);
      this.setState({
        watchedMovieSet: set,
      });
    } else {
      const set = this.state.starMovieSet;
      set.add(id);
      this.setState({
        starMovieSet: set,
      });
    }
  }

  setUnviewOrUnstarState = (movieListName, id) => {
    if (movieListName === watchedMovieName) {
      const set = this.state.watchedMovieSet;
      set.delete(id);
      this.setState({
        watchedMovieSet: set,
      });
    } else {
      const set = this.state.starMovieSet;
      set.delete(id);
      this.setState({
        starMovieSet: set,
      });
    }
  }

  numberToBoolean = (num) => {
    if (num > 0) {
      return true;
    }
    return false;
  };

  searchMovie = (searchText) => {
    this.setState({
      searchText,
    });
  };

  render() {
    const columns = [
      {
        title: '电影名称',
        key: 'name',
        dataIndex: 'title',
        render: (text, record) => {
          return <a href={record.url} target={'_blank'}>{text}</a>;
        },
        sorter: (a, b) => a.title.localeCompare(b.title),
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
        dataIndex: 'movieYear',
        sorter: (a, b) => a.movieYear - b.movieYear,
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
          let isChecked = false;
          if (record && record.movieId && this.state.watchedMovieSet.has(record.movieId)) {
            isChecked = true;
          }
          return (<span>
            {isChecked &&
            <Button
              shape="circle"
              icon="eye"
              onClick={() => {this.updateCheck(!isChecked,
              record.movieId, watchedMovieName);}}
            />}
            {!isChecked &&
            <Button
              shape="circle"
              icon="eye-o"
              onClick={() => {this.updateCheck(!isChecked,
                record.movieId, watchedMovieName);}}
            />}
            </span>);
        },
        width: 100,
        filters: [
          {
            text: '已观影',
            value: 1,
          },
          {
            text: '未观影',
            value: 0,
          },
        ],
        filterMultiple: false,
        onFilter: (value, record) => record.viewed === this.numberToBoolean(value),
      },
      {
        title: '想看',
        key: 'star',
        dataIndex: 'star',
        render: (text, record) => {
          let isChecked = false;
          if (record && record.movieId && this.state.starMovieSet.has(record.movieId)) {
            isChecked = true;
          }
          return (<span>
            {isChecked &&
            <Button
              shape="circle"
              icon="heart"
              onClick={() => {this.updateCheck(!isChecked,
                record.movieId, starMovieName);}}
            />}
            {!isChecked &&
            <Button
              shape="circle"
              icon="heart-o"
              onClick={() => {this.updateCheck(!isChecked,
                record.movieId, starMovieName);}}
            />}
          </span>);
        },
        width: 100,
        filters: [
          {
            text: '想看',
            value: 1,
          },
          {
            text: '未知',
            value: 0,
          },
        ],
        filterMultiple: false,
        onFilter: (value, record) => record.star === this.numberToBoolean(value),
      },
    ];

    let { data } = this.props;
    if (data !== null) {
      const { searchText } = this.state;
      if (searchText !== '') {
        data = data.filter(item => item.title.includes(searchText));
      }
      data.map((item, index) => {
        item.key = index;
      });
    }

    const { watchedMovieSet, starMovieSet, current, pageSize } = this.state;
    if (watchedMovieSet) {
      data.map((item) => {
        if (watchedMovieSet.has(item.movieId)) {
          item.viewed = true;
        }
      });
    }

    if (starMovieSet) {
      data.map((item) => {
        if (starMovieSet.has(item.movieId)) {
          item.star = true;
        }
      });
    }

    return (
      <div>
        <div style={{ marginBottom: 8}} >
          <Search
            placeholder="search movie by name"
            onSearch={this.searchMovie}
            style={{ width: 200 }}
          />
        </div>
        <Table
          columns={columns}
          dataSource={data}
          pagination={{
            current,
            pageSize,
            showSizeChanger: true,
            onShowSizeChange: this.onShowSizeChange,
            pageSizeOptions: ['6', '8', '10'],
            showQuickJumper: true,
            onChange: this.onChange,
          }}
          expandedRowRender={record => <MovieDetail record={record} />}
          scroll={{ y: '70vh' }}
        />
      </div>
    );
  }
}

function mapStateToProps() {
  return {
    // data: state.info.movieRecentList.data,
  };
}

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(Main);
