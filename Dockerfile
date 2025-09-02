# Step 1: Build the application
FROM maven:3.9.6-eclipse-temurin-22 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run the application
FROM eclipse-temurin:22
WORKDIR /app
COPY --from=build /app/target/*.jar demo.jar
ENTRYPOINT ["java", "-jar", "demo.jar"]
EXPOSE 8081
