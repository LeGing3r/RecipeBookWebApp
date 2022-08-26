#!/bin/bash

docker compose build recipebook-backend shoppinglist-backend

docker compose up -d recipebook-backend shoppinglist-backend

