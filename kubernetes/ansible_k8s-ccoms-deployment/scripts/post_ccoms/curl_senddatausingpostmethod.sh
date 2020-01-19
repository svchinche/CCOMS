#!/bin/bash

SCRIPT=$(readlink -f "$0")
SCRIPTPATH=$(dirname "$SCRIPT")

export NO_PROXY=mum00aqm,localhost,127.0.0.1
HOST_NAME=10.180.86.187
PROXY_PORT=8111

JSON_FILES_DIR="$SCRIPTPATH/json_files"
EMP_JSONFILE="$JSON_FILES_DIR/emps.json"
DEPT_JSONFILE="$JSON_FILES_DIR/departments.json"
ORG_JSONFILE="$JSON_FILES_DIR/organization.json"


curl -X POST -H "Content-Type: application/json" -d @$EMP_JSONFILE http://$HOST_NAME:$PROXY_PORT/emp/api/addemps
curl -X POST -H "Content-Type: application/json" -d @$DEPT_JSONFILE http://$HOST_NAME:$PROXY_PORT/dept/api/adddepts
curl -X POST -H "Content-Type: application/json" -d @$ORG_JSONFILE http://$HOST_NAME:$PROXY_PORT/org/api/addorgs
