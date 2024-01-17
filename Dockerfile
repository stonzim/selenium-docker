FROM bellsoft/liberica-openjdk-alpine:21

## Install curl jq
RUN apk add curl jq

## Workspace
WORKDIR /home/selenium-docker

## Add required files
ADD target/docker-resources     .
ADD runner.sh                   runner.sh

## Run tests
ENTRYPOINT sh runner.sh