#!/bin/bash

# package jar
mvn clean package -DskipTests=true

# move jar
cp target/movie-helper-1.0.0.jar ./docker/movie-helper-1.0.0.jar

# move to docker folder
cd docker

DATETIME=$(date "+%Y%m%d")

docker build -t moviehelper:$DATETIME .

