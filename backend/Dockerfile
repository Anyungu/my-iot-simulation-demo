# Stage 1
# initialize build and set base image for first stage
FROM maven:3.6.3-adoptopenjdk-11 as stage1

# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

# set working directory
WORKDIR /opt/demo

# copy just pom.xml
COPY pom.xml .

# go-offline using the pom.xml
# RUN mvn dependency:go-offline

# copy your other files
COPY ./src ./src

# compile the source code and package it in a jar file
RUN mvn clean install -Dmaven.test.skip=true



# Stage 2

# Start with a base image containing Java runtime
FROM openjdk:11.0-slim

# Add Maintainer Info
LABEL maintainer="anyungucw@gmail.com"

# Add a volume pointing to /tmp
WORKDIR /opt/demo

# Make port 8080 available to the world outside this container
EXPOSE 88

COPY --from=stage1 /opt/demo/target/mapping-0.0.1-SNAPSHOT.jar /opt/demo

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/demo/mapping-0.0.1-SNAPSHOT.jar"]
