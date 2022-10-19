#!/bin/bash

docker-compose up -d mongo1 mongo2 mongo3

sleep 10

docker exec mongo1 ./rs-init.sh

sleep 10