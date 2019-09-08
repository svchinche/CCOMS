#!/bin/bash

#### Script to download  the resource


##################################################

username="admin"
password="admin123"
ENC_PASSWD=`echo -n $username:$password | base64`
##################################################

NEXUS_URL="worker-node1:8081"
### to get the latest repository based on the search
SORT="repository" 
### Search criteria
REPOSITORY="maven-snapshots"
PROJECT_NAME="quickstart"
GROUP="com.vogella.maven"
VERSION="v9.1.20*"
MAVEN_EXTENSION="jar"
OUTPUT_FILE="quickstart.jar"
FINAL_URL="http://$NEXUS_URL/service/rest/v1/search/assets/download/?sort=$SORT&repository=$REPOSITORY&name=$PROJECT_NAME&group=$GROUP&version=$VERSION&maven.extension=$MAVEN_EXTENSION"


status_code=$(curl -v -H "Authorization: Basic $ENC_PASSWD" -L -X GET $FINAL_URL -o $OUTPUT_FILE)

if [[ "$status_code" -ne 200 ]] ; then
  #echo "Site status changed to $status_code" | mail -s "SITE STATUS CHECKER" "my_email@email.com" -r "STATUS_CHECKER"
   echo "Site status changed to $status_code"
else
  exit 0
fi
