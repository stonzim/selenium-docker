pipeline {
    agent any
    stages {
        stage("Build Jar") {
            steps {
                sh "mvn clean package -DskipTests"
            }
        }
        stage("Build Image") {
            steps {
                sh "docker build -t=stonzim/selenium ."
            }
        }
        stage("Push Image") {
            environment {
                DOCKER_HUB = credentials("dockerhub-creds")
            }
            steps {
                sh 'echo ${DOCKER_HUB_PSW} | docker login -u ${DOCKER_HUB_USR} --password-stdin'
                sh "docker push stonzim/selenium"
            }
        }
    }
    post {
        always {
            sh "docker logout"
        }
    }
}