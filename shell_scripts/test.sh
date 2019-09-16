#!/bin/bash

### Dependent packages for the script 
## 1. jq
## 2. moreutils ( provides sponge utility ) 

### Variable declaration

function usage { 
echo -e "\nUSAGE: $0 RETENTION_DAYS PREFIX JSON_FILE_PATH"
echo "
     RETENTION_DAYS  A number that represent how long file should stay
     PREFIX          For object name
     JSON_FILE_PATH  File required to provide input "
}

if [ $# -ne 3 ]; then
usage
exit 123
fi


RETENTION_PERIOD=${1}
PREFIX_STRING=${2}
JSON_FILE_PATH=${3}
OBJECT_COUNT=$(jq '. | length' $JSON_FILE_PATH)
TODAYS_DATE_SECONDS=$(date +%s)
rm -f /tmp/result.txt


for i in `seq 0 1 ${OBJECT_COUNT}`
do 
OBJECT_NAME=$( jq ".[$i].name" $JSON_FILE_PATH | tr -d '"' )
OBJECT_TIMESTAMP=$( jq ".[$i].creationTimestamp" $JSON_FILE_PATH | tr -d '"')
OBJECT_TIME_SECONDS=`date -d $OBJECT_TIMESTAMP +%s | tr -d "'"`
DIFF=$(( ($TODAYS_DATE_SECONDS - OBJECT_TIME_SECONDS)/86400 ))

if [ ${DIFF} -ge ${RETENTION_PERIOD} -a ${OBJECT_TIMESTAMP} != 'null' ]; 
then
jq ".[$i].name = \"${PREFIX_STRING}${OBJECT_NAME}\"" $JSON_FILE_PATH  |sponge $JSON_FILE_PATH
echo $OBJECT_NAME $OBJECT_TIMESTAMP >> /tmp/result.txt
fi
done

COUNT=$(cat /tmp/result.txt | wc -l)
echo total number of images older than $RETENTION_PERIOD days $COUNT

