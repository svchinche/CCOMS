FROM openjdk:8-jdk-alpine

VOLUME /tmp

ENV APP_FILE ProxyServer-MicroService.war

EXPOSE 8111

ADD target/${APP_FILE} ${APP_FILE}

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ProxyServer-MicroService.war"]
