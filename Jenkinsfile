pipeline {
    agent any 
    tools { 
        maven 'maven' 
        jdk 'jdk17' 
    }
    parameters {
          string(name: 'gitUrl', defaultValue: 'https://github.com/abhijitjadeja/spring-batch-demo.git', description: 'git url')
          string(name: 'tag', defaultValue: 'master', description: 'git tag')
    }

    stages {
        
        stage ('Build') {
            steps {
                git '${params.gitUrl}'
                git checkout '${params.tag}'
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
