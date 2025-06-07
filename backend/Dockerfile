FROM openjdk:17
# Dynamically asigns the jar filepath
ARG JAR_FILE=target/*.jar    
# copies the generated jar to the container
COPY ${JAR_FILE} app.jar     
# Declares that the containerlistens on port 8082
EXPOSE 8082
# executes the springboot application
ENTRYPOINT ["java", "-jar", "app.jar"]