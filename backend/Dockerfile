FROM openjdk:11.0-slim

# Add Maintainer Info
LABEL maintainer="anyungucw@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

ARG JAR_FILE=target/mapping-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
COPY ${JAR_FILE} mapping.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/mapping.jar"]

