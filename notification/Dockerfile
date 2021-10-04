FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} notification.jar
ENTRYPOINT ["java","-jar","/notification.jar"]
EXPOSE 8085
