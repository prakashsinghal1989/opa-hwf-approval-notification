FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} email.instance.creation.jar
ENTRYPOINT ["java","-jar","/email.instance.creation.jar"]
EXPOSE 8085
