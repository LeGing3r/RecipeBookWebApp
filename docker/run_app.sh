#!/bin/bash

docker compose build $1
docker compose up -d $1

echo "Press enter to exit"

read