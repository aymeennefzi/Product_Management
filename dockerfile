FROM openjdk:17

# Installation de curl
RUN apt-get update && apt-get install -y curl

# Téléchargement du fichier JAR depuis Nexus
RUN curl -o DevOps_Project-1.0.jar -L "http://votre_nexus_url/chemin/vers/votre/DevOps_Project-1.0.jar"

# Configuration de l'application
EXPOSE 8082
WORKDIR /app

# Commande d'exécution de l'application
ENTRYPOINT ["java", "-jar", "DevOps_Project-1.0.jar"]
