
FROM maven:latest AS build

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

#java 21
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/target/student-management-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

