# syntax=docker/dockerfile:1

FROM openjdk:17 AS builder
RUN microdnf install findutils
WORKDIR /source
COPY . .
RUN ./gradlew build

FROM openjdk:17
WORKDIR /app
COPY --from=builder /source/WebService/build/libs/WebService-1.0-SNAPSHOT.jar WebService-1.0-SNAPSHOT.jar

CMD ["java", "-jar", "/app/WebService-1.0-SNAPSHOT.jar", "--spring.profiles.active=docker"]