FROM openjdk:17
EXPOSE 8082
WORKDIR /app
RUN apt-get update && apt-get install -y curl
RUN curl -o DevOps_Project-1.0.jar -L "http://172.16.1.70:8081/repository/maven-releases/tn/esprit/DevOps_Project/1.0/DevOps_Project-1.0.jar"
ENTRYPOINT ["java", "-jar", "DevOps_Project-1.0.jar"]
