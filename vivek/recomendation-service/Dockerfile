FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} recomendation-srvc.jar
ENTRYPOINT ["java","-jar","/recomendation-srvc.jar"]
EXPOSE 8888