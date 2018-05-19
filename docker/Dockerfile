FROM openjdk:alpine

RUN apk --update add bash

COPY . /usr/src/movie-helper

WORKDIR /usr/src/movie-helper

EXPOSE 8080

CMD ["bash", "start.sh"]