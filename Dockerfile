# Use an official OpenJDK runtime as the base image
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /project/java-docker-app/ceraphi_jar

# Copy the JAR file into the container
COPY my-app.jar /ceraphi_jar/my-app.jar

# Command to run your Java application
CMD ["java", "-jar", "my-app.jar"]