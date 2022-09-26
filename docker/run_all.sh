#!/bin/bash

docker compose build recipebook-backend shoppinglist-backend recipebook-frontend

docker compose up -d recipebook-backend shoppinglist-backend recipebook-frontend
