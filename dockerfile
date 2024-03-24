FROM openjdk:17-alpine

# Installation de wget (si ce n'est pas déjà installé)
RUN apk update && apk add --no-cache wget

# Configuration de l'application
EXPOSE 8082
WORKDIR /app

# Téléchargement du fichier JAR depuis Nexus
RUN wget -O DevOps_Project-1.0.jar "http://172.16.1.70:8081/repository/maven-releases/tn/esprit/DevOps_Project/1.0/DevOps_Project-1.0.jar"

# Commande d'exécution de l'application
ENTRYPOINT ["java", "-jar", "DevOps_Project-1.0.jar"]
