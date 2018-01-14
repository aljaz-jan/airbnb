FROM openjdk:8-jre-alpine

RUN mkdir /app

WORKDIR /app

ARG JAR_FILE
ADD target/${JAR_FILE} /app/realEstateDistance.jar

EXPOSE 8083

CMD ["java", "-jar", "realEstateDistance.jar"]