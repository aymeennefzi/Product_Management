pipeline {
    agent any

    tools {
        maven 'M2_HOME'
    }

    stages {

        stage('Checkout Git repository') {
            steps {
                echo 'Pulling ';
                git branch: 'master', credentialsId: 'PAT_Jenkins' , url: 'https://github.com/aymeennefzi/Devops_Project.git';
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
                    def scannerHome = tool 'scanner'
                         withSonarQubeEnv {
                         sh "${scannerHome}/bin/sonar-scanner"
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
