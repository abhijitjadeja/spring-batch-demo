pipeline {
    agent any 
    tools { 
        maven 'maven' 
        jdk 'jdk17' 
    }
    parameters {
        string(name: 'gitUrl', defaultValue: 'https://github.com/abhijitjadeja/spring-batch-demo.git', description: 'git url')
        string(name: 'tag', defaultValue: '*/master', description: 'git tag')
        string(name: 'version',defaultValue:'1.0',description:'buildVersion')
    }
    stages {
        stage('checkout'){
            steps{
                deleteDir()
                checkout([$class: 'GitSCM',
                branches: [[name: "${params.tag}"]],
                userRemoteConfigs: [[url: "${params.gitUrl}"]]])                
            }
        
        }
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
        stage('build container and push to the repository') {
            steps {
                script{
                    sh "docker build . -t spring-batch-demo:${params.version}"
                }
            }
        }
    }
}
