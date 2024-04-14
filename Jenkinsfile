pipeline {
    agent any

    tools {
        maven 'M2_HOME'
        
    }

    environment {
        SCANNER_HOME = tool 'scanner'
        SONAR_TOKEN = credentials('scanner')
        SONAR_URL = 'http://172.16.1.70:9000'
        DOCKERHUB_TOKEN = credentials('dockerhub')
    }

    stages {
        stage('Checkout Git repository') {
            steps {
                echo 'Pulling'
                git branch: 'master', credentialsId: 'PAT_Jenkins', url: 'https://github.com/aymeennefzi/Product_Management.git'
            }
        }

        stage('Maven Clean Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Tests - JUnit/Mockito') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build package') {
            steps {
                sh 'mvn package'
            }
        }

        stage('Maven Install') {
            steps {
                sh 'mvn install'
            }
        }

        stage('Rapport JaCoCo') {
            steps {
                sh 'mvn jacoco:report'
            }
        }
        stage('JaCoCo coverage report') {
            steps {
                step([$class: 'JacocoPublisher',
                      execPattern: '**/target/jacoco.exec',
                      classPattern: '**/classes',
                      sourcePattern: '**/src',
                      exclusionPattern: '*/target/**/,**/*Test*,**/*_javassist/**'
                ])
            }
        }
         stage('SonarQube analysis') {
            steps {
                script {
                    sh "mvn sonar:sonar -Dsonar.login=${SONAR_TOKEN} -Dsonar.host.url=${SONAR_URL}"
                }
            }
        }
        stage('Deploy to Nexus') {
            steps {
                sh 'mvn deploy'
            }
        }
        stage('Build Docker Image') {
             steps {
                 script {
                     def dockerImage=docker.build("product_management" , "-f Dockerfile .")
                 }
             }
        }
       stage('Push Docker Image to DockerHub') {
           steps {
               script {
                   withCredentials([string(credentialsId: 'dockerhub', variable: 'dockerpwd')]) {
                       sh '''
                       docker login -u aymennefzi99 -p "$dockerpwd"
                       docker tag product_management:latest aymennefzi99/product_management:latest
                       docker push aymennefzi99/product_management:latest
                       '''
                   }
               }
           }
       }
       stage('Docker compose') {
           steps {
               script {
                   sh 'docker-compose up -d'
                   def runningContainers = sh(script: "docker ps --format '{{.Names}}'", returnStdout: true).trim()
                   echo "Conteneurs en cours d'exécution : ${runningContainers}"
                   def containersToCheck = ["nom_du_conteneur_1", "nom_du_conteneur_2", "nom_du_conteneur_3"]
                   def containersReady = false
                   def maxAttempts = 12
                   for (int i = 0; i < maxAttempts; i++) {
                       sleep 10
                       containersReady = true
                       containersToCheck.each { container ->
                           if (!runningContainers.contains(container)) {
                               containersReady = false
                           }
                       }
                       if (containersReady) {
                           break
                       }
                   }
                   if (containersReady) {
                       echo "Tous les conteneurs sont en cours d'exécution."
                   } else {
                       echo "Certains conteneurs ne sont pas en cours d'exécution."
                   }
               }
           }
       }
       stage('Start prometheus') {
            steps {
                script {
                    sh 'docker start 1d0792b4a1ee'
                }
            }
        }
        stage('Start Grafana') {
            steps {
                script {
                    sh 'docker start 39a8a09167e6'
                }
            }
        } 
        stage('Email Notification') {
            steps{
                mail bcc: '', body: '''Stage: GIT Pull
                - Pulling from Git...

                Stage: Maven Clean Compile
                - Building Spring project...

                Stage: Test - JUNIT/MOCKITO
                - Testing Spring project...

                Stage: JaCoCo Report
                - Generating JaCoCo Coverage Report...

                Stage: SonarQube Analysis
                - Running Sonarqube analysis...

                Stage: Deploy to Nexus
                - Deploying to Nexus...

                Stage: Build Docker Image
                - Building Docker image for the application...

                Stage: Push Docker Image
                - Pushing Docker image to Docker Hub...

                Stage: Docker Compose
                - Running Docker Compose...

                Stage: Monitoring Services G/P
                - Starting Prometheus and Grafana...

                Final Report: The pipeline has completed successfully. No action required''', cc: '', from: '', replyTo: '', subject: 'Succès de la pipeline product_management-Project', to: 'aymennefzi67@gmail.com'
            }
        }  
    }
}
