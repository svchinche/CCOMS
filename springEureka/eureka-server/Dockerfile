FROM openjdk:8-jdk-alpine

VOLUME /tmp

EXPOSE 8761

ARG JAR_FILE=target/Eureka-Server.jar

## Add application jar to container
ADD ${JAR_FILE} Eureka-Server.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/Eureka-Server.jar"]
