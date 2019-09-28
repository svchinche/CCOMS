FROM openjdk:8-jdk-alpine

VOLUME /tmp

EXPOSE 8100

ARG JAR_FILE=target/Capitals-Service.jar

## Add application jar to container
ADD ${JAR_FILE} Capitals-Service.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/Capitals-Service.jar"]
