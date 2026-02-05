# Use OpenJDK 17 base image
FROM openjdk:17

# Set working directory inside the container
WORKDIR /app

# Copy the fat JAR from target folder
COPY target/*.jar app.jar

# Expose the service port (change if your Inventory Service runs on another port)
EXPOSE 8085

# Run the Spring Boot application
ENTRYPOINT ["java","-jar","app.jar"]
