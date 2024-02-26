FROM alpine:latest
RUN apk add --no-cache openjdk8
COPY target/*.jar /app.jar
ENTRYPOINT ["/bin/sh","-c","java -Dfile.encoding=utf8 -jar app.jar"]
