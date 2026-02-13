FROM gradle:8.7-jdk AS build

WORKDIR /app

COPY . .

ENV JAVA_HOME=/opt/java/openjdk

RUN gradle build --no-daemon -x test

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar usuario.jar

EXPOSE 8080

CMD ["java", "-jar", "usuario.jar"]