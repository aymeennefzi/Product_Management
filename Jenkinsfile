pipeline {
    agent any

    tools {
        maven 'M2_HOME'
    }

    environment {
            SCANNER_HOME = tool 'scannerHome'
        }

    stages {

        stage('Checkout Git repository') {
            steps {
                echo 'Pulling ';
                git branch: 'master', credentialsId: 'PAT_Jenkins' , url: 'https://github.com/aymeennefzi/Product_Management.git';
            }
        }

        stage('Maven Clean Compile') {
            steps {
                sh 'mvn clean'
                echo 'Running Maven Compile'
                sh 'mvn compile'
            }
        }

        stage('SonarQube Analysis') {
            steps{
                script {
                  withSonarQubeEnv('SonarQubeBenIsmail') {
                    sh 'mvn sonar:sonar'
                }
                }
            }
        }

        stage('Maven Install') {
            steps {
                  sh 'mvn install -DskipTests=true'
            }
        }
}

}
