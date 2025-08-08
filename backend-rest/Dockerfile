ARG APP_NAME=tienda-online

FROM openjdk:17-jdk-alpine as builder

ARG APP_NAME

WORKDIR /app/$APP_NAME

COPY ./pom.xml /app
COPY ./.mvn ./.mvn
COPY ./mvnw .
COPY ./pom.xml .

RUN chmod +x ./mvnw

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline

COPY ./src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:21-jdk-slim

ARG APP_NAME

WORKDIR /app

RUN mkdir ./logs

ARG TARGET_FOLDER=/app/$APP_NAME/target
COPY --from=builder $TARGET_FOLDER/tienda-online-0.0.1-SNAPSHOT.jar .
ENV PORT 8001
EXPOSE $PORT

CMD ["java", "-jar", "tienda-online-0.0.1-SNAPSHOT.jar"]