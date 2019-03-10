FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ARG DEPENDENCY=target/dependency
ARG JAR_FILE=target/eventapp-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
COPY ${DEPENDENCY}/META-INF /app/META-INF
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
