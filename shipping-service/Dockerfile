FROM amazoncorretto:17-alpine
WORKDIR /app
COPY target/shipping-service-1.0-SNAPSHOT.jar shipping-service.jar
ENTRYPOINT ["java", "-jar", "shipping-service.jar"]

