FROM alpine:latest
RUN apk add --no-cache tzdata openjdk8-jdk
COPY target/*.jar /app.jar
ENTRYPOINT ["/bin/sh","-c","java -Dfile.encoding=utf8 -jar app.jar"]
