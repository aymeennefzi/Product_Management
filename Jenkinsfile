pipeline {
    agent any

    tools {
        maven 'M2_HOME'
        
    }

    environment {
        SCANNER_HOME = tool 'scanner'
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

        stage("Sonarqube Analysis") {
            steps {
                withSonarQubeEnv('sonar-server') {
                    sh ''' 
                    $SCANNER_HOME/bin/scanner 
                    -Dsonar.projectName=DevopsProject 
                    -Dsonar.java.binaries=target/classes
                    -Dsonar.projectKey=devops
                    -Dsonar.login=squ_e4fbd64375e75171a68a792d1449dec732919d79
                    '''
                }
            }
        }
    }
}
