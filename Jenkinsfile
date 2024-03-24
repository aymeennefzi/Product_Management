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
        stage('Build Docker Image (DevOps_Project)') {
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

    }

}
