pipeline {
    agent none
    stages {
        stage("Build Jar") {
            agent {
                docker {
                    image "maven:3.9.3-eclipse-temurin-17-focal"
                }
            }
            steps {
                sh "mvn clean package -DskipTests"
            }
        }
        stage("Build Image") {
            steps {
                steps {
                    script {
                        app = docker.build("stonzim/selenium")
                    }
                }
            }
        }
        stage("Push Image") {
            steps {
                script {
                    docker.withRegistry("", "dockerhub-creds") {
                        app.push("latest")
                    }
                }
            }
        }
    }
}