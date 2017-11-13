import React, { Component } from 'react';
import '../styles/style.css';

class MovieDetail extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const { record } = this.props;
        console.log(record.imageLarge);
        return (
          <div className={'extra-info'}>
            <img src={record.imageLarge} />
            <p><a href={record.imageLarge} target={'_blank'}>{record.title}</a></p>
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
    }
}

export default MovieDetail;