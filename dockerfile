FROM ubuntu:latest
# Installation de Java et Curl
RUN apt-get update && apt-get install -y openjdk-17-jdk curl
# Configuration de l'application
EXPOSE 8082
WORKDIR /app
# Téléchargement du fichier JAR de l'application
RUN curl -o DevOps_Project-1.0.jar -L "http://172.16.1.70:8081/repository/maven-releases/tn/esprit/DevOps_Project/1.0/DevOps_Project-1.0.jar"

# Commande d'exécution de l'application
ENTRYPOINT ["java", "-jar", "DevOps_Project-1.0.jar"]
