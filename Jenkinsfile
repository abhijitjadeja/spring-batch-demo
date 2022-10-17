pipeline {
    agent any 
    tools { 
        maven 'maven' 
        jdk 'jdk17' 
    }
    parameters {
        string(name: 'gitUrl', defaultValue: 'https://github.com/abhijitjadeja/spring-batch-demo.git', description: 'git url')
        string(name: 'tag', defaultValue: '*/master', description: 'git tag')
        string(name: 'name',defaultValue: 'spring-batch-demo',description: 'application name - will be used to name image')
        string(name: 'version',defaultValue:'1.0',description:'application version - will be used to tag image')
        string(name: 'region', defaultValue: 'NA', description: 'region where deployment happens')
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
            when{
                expression { params.region == 'devint' }
            }
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
           when{
                expression { params.region == 'devint' }
            }
            steps{
                script{
                    docker.withRegistry('', 'docker-login') {
                        docker.build("sleepingm/${params.name}:${params.version}").push()
                    }
                }
            }
        }
        stage('deploy configmap and container in kubernetes') {
           when{
                expression { params.region == 'devint' || params.region == 'prod' }
            }
            steps{
                script{
                 //sh "kubectl apply -f kubeconfig/${params.region}"
                 sh "kubectl apply -k kustomize/overlays/${params.region}"
                }
            }
        }
    }
}
