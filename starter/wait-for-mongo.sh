#!/bin/bash

set -e

host="$1"
port="$2"
shift

while [[ $(curl -o /dev/null -s -w "%{http_code}\n" "http://${host}:${port}") != "200" ]]; do
  >&2 echo "Mongo is unavailable - sleeping"
  sleep 2
done

>&2 echo "Mongo is up"

exit
