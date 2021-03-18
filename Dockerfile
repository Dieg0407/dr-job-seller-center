FROM maven:3.6.3-jdk-11 as build

WORKDIR /binaries
COPY src src
COPY pom.xml pom.xml

RUN mvn clean package -DskipTests

FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /app

COPY --from=build /binaries/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]