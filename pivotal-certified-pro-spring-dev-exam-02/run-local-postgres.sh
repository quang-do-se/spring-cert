#!/bin/bash

docker run \
  --name local-postgres \
  -e POSTGRES_USER=sample \
  -e POSTGRES_PASSWORD=sample \
  -e POSTGRES_DB=spring \
  -p 5432:5432 \
  -d postgres