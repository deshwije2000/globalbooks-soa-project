# Use a Java 17 base image
FROM amazoncorretto:17-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the executable JAR file into the container
COPY target/payments-service-1.0-SNAPSHOT.jar payments-service.jar

# Command to run the service when the container launches
ENTRYPOINT ["java", "-jar", "payments-service.jar"]

