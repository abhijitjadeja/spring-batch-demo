pipeline {
    agent any 
    stages {
        stage ('Build') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true install' 
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml' 
                }
            }
        }       
        stage('build container') {
            steps {
                script{
                    sh "docker build . -t spring-batch-demo:1"
                }
            }
        }
    }
}
