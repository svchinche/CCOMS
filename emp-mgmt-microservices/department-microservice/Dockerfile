FROM openjdk:8-jdk-alpine

VOLUME /tmp

ENV APP_FILE Department-MicroService.war

EXPOSE 8080

ADD target/${APP_FILE} ${APP_FILE}

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/Employee-MicroService.war"]
