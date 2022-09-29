#Stage 1
# initialize build and set base image for first stage
FROM maven:3.6.3-adoptopenjdk-11 as builder
# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
# set working directory
WORKDIR /tmp
# copy just pom.xml
COPY pom.xml .
# copy your other files
COPY ./src ./src
# compile the source code and package it in a jar file
RUN mvn package -Dmaven.test.skip=true
#Stage 2
# set base image for second stage
FROM adoptopenjdk/openjdk11:jre-11.0.9_11-alpine
# set deployment directory
WORKDIR /tmp
# copy over the built artifact from the maven image
COPY --from=builder /tmp/target/*.jar /tmp/app.jar

ENTRYPOINT ["java","-jar","/tmp/app.jar"]
