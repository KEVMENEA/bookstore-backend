FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Create the upload directory and set permissions
RUN mkdir -p /app/uploads/books && chmod -R 777 /app/uploads

EXPOSE 8290

# Using shell form to allow environment variable expansion if needed
CMD ["java", "-jar", "app.jar"]
