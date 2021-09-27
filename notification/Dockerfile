FROM openjdk:8
ARG JAR_FILE=target/notification-0.0.1.jar
COPY ${JAR_FILE} notification.jar
ENTRYPOINT ["java","-jar","/notification.jar"]
EXPOSE 8085
