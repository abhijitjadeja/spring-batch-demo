pipeline {
    agent any 
    tools { 
        maven 'maven' 
        jdk 'jdk17' 
    }
    parameters {
          string(name: 'gitUrl', defaultValue: 'none', description: 'git url')
    }
    stages {
        
        stage ('Build') {
            steps {
                cleanWs
                git '${params.gitUrl}'
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
