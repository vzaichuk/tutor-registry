#!/bin/bash

set -e

host="$1"
port="$2"
waitTime="$3"
shift

while [[ $(curl -o /dev/null -s -w "%{http_code}\n" "${host}:${port}") != "200" ]]; do
  >&2 echo "Discovery is unavailable - sleeping"
  sleep ${waitTime}
done

>&2 echo "Discovery is up"

exit
