#!/bin/bash

set -e

host="$1"
port="$2"
waitTime="$3"
shift

while [[ $(nc -zv "${host}" "${port}" 2>&1) != *"[tcp/mysql] succeeded"* ]]; do
  >&2 echo "Mysql is unavailable - sleeping"
  mes=$(nc -zv "${host}" "${port}" 2>&1) || echo "none" 2>&1
  echo "Status -- ${mes}"
  sleep ${waitTime}
done

>&2 echo "Mysql is up"

exit
