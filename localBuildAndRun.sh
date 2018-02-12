#!/usr/bin/env bash

#Script will build and add jar to the lib folder
./gradlew clean build

#building the image
docker build -t rks/shopping-cart .

#running the container
docker run  -p 8080:8080 rks/shopping-cart