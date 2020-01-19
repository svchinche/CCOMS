FROM openjdk:8-jdk-alpine

VOLUME /tmp

ENV APP_FILE ConfigServer-MicroService.war

EXPOSE 8888

ADD target/${APP_FILE} ${APP_FILE}

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ConfigServer-MicroService.war"]
