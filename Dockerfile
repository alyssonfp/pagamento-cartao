FROM quay.io/quarkus/ubi-quarkus-native-image:22.3-java17 AS build

WORKDIR /work

COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./

COPY src ./src

RUN ./mvnw package -Pnative -DskipTests

FROM quay.io/quarkus/quarkus-micro-image:1.0

WORKDIR /work

COPY --from=build /work/target/*-runner /work/application

EXPOSE 8080

CMD ["./application"]
