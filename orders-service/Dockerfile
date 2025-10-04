# Use an official OpenJDK 17 runtime as a base image
FROM amazoncorretto:17-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the executable JAR file into the container at /app
COPY target/orders-service-1.0-SNAPSHOT.jar orders-service.jar

# Command to run the service when the container launches
ENTRYPOINT ["java", "-jar", "orders-service.jar"]

