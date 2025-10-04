# Use an official Tomcat 9 image as a base
FROM tomcat:9.0-jdk17-corretto

# Remove the default Tomcat welcome page
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the WAR file from your target folder into Tomcat's webapps directory
COPY target/catalog-service.war /usr/local/tomcat/webapps/catalog-service.war


