import React, { Component } from 'react';
import styled from 'styled-components';
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
              <h3>{'主演'}</h3>
              {
                record.casts.split(',').map((cast) => {
                  return <Author key={cast} >{cast}</Author>
                })
              }
              <h3>{'导演'}</h3>
              {
                record.directors.split(',').map((director) => {
                  return <Author key={director} >{director}</Author>
                })
              }
              <hr/>
              <h3>{'简介'}</h3>
              <p>{record.summary}</p>
              <h3>{'国家'}</h3>
              <p>{record.countries}</p>
            </div>
          </div>
        );
    }
}

const Author = styled.p`
  margin-bottom: 4px;
  margin-top: 0;
  padding: 0;
`;

export default MovieDetail;