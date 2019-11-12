#!/bin/bash

export NO_PROXY=mum00aqm,localhost,127.0.0.1,mum00aqm.in.oracle.com
HOST_NAME=10.180.86.187
PROXY_PORT=8111
EMP_JSONFILE="../../../org_management_system/employee-microservice/emps.json"
DEPT_JSONFILE="../../../org_management_system/department-microservice/departments.json"
ORG_JSONFILE="../../../org_management_system/organization-microservice/organization.json"


curl -X POST -H "Content-Type: application/json" -d @$EMP_JSONFILE http://$HOST_NAME:$PROXY_PORT/emp/api/addemps
curl -X POST -H "Content-Type: application/json" -d @$DEPT_JSONFILE http://$HOST_NAME:$PROXY_PORT/dept/api/adddepts
curl -X POST -H "Content-Type: application/json" -d @$ORG_JSONFILE http://$HOST_NAME:$PROXY_PORT/org/api/addorgs
