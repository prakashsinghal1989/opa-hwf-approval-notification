FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} camunda-project.jar
ENTRYPOINT ["java","-jar","/camunda-project.jar"]
EXPOSE 8080