#This will take the jar file built during the build process
# set base image for second stage
FROM adoptopenjdk/openjdk11:jre-11.0.9_11-alpine
# set deployment directory
WORKDIR /tmp
# copy over the built artifact from the maven image
COPY target/*.jar /tmp/app.jar

ENTRYPOINT ["java","-jar","-Dservice.url=$SERVICE_URL","/tmp/app.jar"]
