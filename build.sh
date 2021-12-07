#!/bin/bash

type=$1

./gradlew :discovery:build &&
./gradlew :gateway:build &&
./gradlew :authentication:build &&
./gradlew :account:build &&
docker-compose build &&
docker-compose up

