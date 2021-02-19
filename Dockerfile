FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=marketplace-0.0.1-SNAPSHOT.jar
COPY /target/${JAR_FILE} docker-marketplace.jar
ENTRYPOINT ["java", "-jar", "docker-marketplace.jar"]