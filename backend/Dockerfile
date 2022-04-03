# Stage 2

# Start with a base image containing Java runtime
FROM openjdk:11.0-slim

# Add Maintainer Info
LABEL maintainer="anyungucw@gmail.com"

# Add a volume pointing to /tmp
WORKDIR /opt/demo

# Make port 8080 available to the world outside this container
EXPOSE 8080

COPY ./target/mapping-0.0.1-SNAPSHOT.jar /opt/demo

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/demo/mapping-0.0.1-SNAPSHOT.jar"]
