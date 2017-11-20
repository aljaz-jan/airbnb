FROM openjdk:8-jre-alpine

RUN mkdir /app

WORKDIR /app

#ADD ./target/realEstate-1.0-SNAPSHOT.jar /app
ARG JAR_FILE
ADD target/${JAR_FILE} /app/realEstate.jar

EXPOSE 8080

CMD ["java", "-jar", "realEstate.jar"]