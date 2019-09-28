FROM openjdk:8-jdk-alpine

VOLUME /tmp

EXPOSE 8000

ARG JAR_FILE=target/Countries-Service.jar

## Add application jar to container
ADD ${JAR_FILE} Countries-Service.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/Countries-Service.jar"]
