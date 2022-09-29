pipeline {
    agent any 
    tools { 
        maven 'maven' 
        jdk 'jdk17' 
    }
    parameters {
        string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
        text(name: 'BIOGRAPHY', defaultValue: '', description: 'Enter some information about the person')
        booleanParam(name: 'TOGGLE', defaultValue: true, description: 'Toggle this value')
        choice(name: 'CHOICE', choices: ['One', 'Two', 'Three'], description: 'Pick something')
        password(name: 'PASSWORD', defaultValue: 'SECRET', description: 'Enter a password')
        string(name: 'gitUrl', defaultValue: 'https://github.com/abhijitjadeja/spring-batch-demo.git', description: 'git url')
        string(name: 'tag', defaultValue: '*/master', description: 'git tag')
    }
    stages {
        stage('checkout'){
            steps{
                echo "Hello ${params.PERSON}"
                echo "${params.gitUrl}"
                echo "${params.tag}"
                deleteDir()
                checkout([$class: 'GitSCM',
                branches: [[name: '${params.tag}']],
                userRemoteConfigs: [[url: '${params.gitUrl}']]])                
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
        stage('build container') {
            steps {
                script{
                    sh "docker build . -t spring-batch-demo:1"
                }
            }
        }
    }
}
