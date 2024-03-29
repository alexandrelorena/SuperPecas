# Base Alpine Linux based image with OpenJDK JRE only

FROM openjdk:21-jdk

LABEL maintainer="Alexandre"

EXPOSE 8080/tcp

COPY target/superpecas-0.0.1-SNAPSHOT.war /app.war

CMD ["java", "-jar", "-Dspring.profiles.active=prd","/app.war"]
