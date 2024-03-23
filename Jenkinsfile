pipeline {
    agent any

    tools {
        maven 'M2_HOME'
    }
    stages {

        stage('Checkout Git repository') {
            steps {
                echo 'Pulling ';
                git branch: 'master', credentialsId: 'PAT_Jenkins' , url: 'https://github.com/aymeennefzi/Product_Management.git';
            }
        }

      }
}

