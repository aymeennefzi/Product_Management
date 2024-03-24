FROM openjdk:17-alpine

RUN apk update && apk add --no-cache wget

EXPOSE 8082
WORKDIR /app

RUN wget -O DevOps_Project-1.0.jar "http://172.16.1.70:8081/repository/maven-releases/tn/esprit/DevOps_Project/1.0/DevOps_Project-1.0.jar" \
    && if [ -f "DevOps_Project-1.0.jar" ]; then echo "Le fichier JAR a été téléchargé avec succès."; else echo "Erreur: Le fichier JAR n'a pas été téléchargé."; exit 1; fi

RUN echo "L'image Docker a été créée avec succès."

ENTRYPOINT ["java", "-jar", "DevOps_Project-1.0.jar"]
