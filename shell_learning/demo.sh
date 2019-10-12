#!/bin/bash
### Variable declaration

RETENTION_PERIOD=${1}
PREFIX_STRING=${2}
JSON_FILE_PATH=${3}
OBJECT_COUNT=$(cat ${JSON_FILE_PATH} | jq '. | length' image.json)
TODAYS_DATE_SECONDS=$(date +%s)
rm -rf /tmp/result.txt

for i in `seq 0 1 ${OBJECT_COUNT}`
do 
OBJECT_NAME=$(cat image.json | jq ".[$i].name")
OBJECT_TIMESTAMP=$(cat image.json | jq ".[$i].creationTimestamp")
OBJECT_TIME_SECONDS=$(date -d ${OBJECT_TIMESTAMP} +%s)
DIFF=$(( ($TODAYS_DATE_SECONDS - OBJECT_TIME_SECONDS)/86400 ))

if [ ${DIFF} -gt ${RETENTION_PERIOD} ]; 
then
echo $OBJECT_NAME $OBJECT_TIMESTAMP > /tmp/result.txt
fi


done

COUNT=$(cat /tmp/result.txt | wc -l)
echo Files  older than $RETENTION_PERIOD days $COUNT
