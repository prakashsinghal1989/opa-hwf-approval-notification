FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} camunda-hwf-engine.jar
ENTRYPOINT ["java","-jar","/camunda-hwf-engine.jar"]
EXPOSE 8080