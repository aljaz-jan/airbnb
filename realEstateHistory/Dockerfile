FROM openjdk:8-jre-alpine

RUN mkdir /app

WORKDIR /app

ARG JAR_FILE
ADD target/${JAR_FILE} /app/realEstateHistory.jar

EXPOSE 8080

CMD ["java", "-jar", "realEstateHistory.jar"]