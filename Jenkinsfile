pipeline {
    agent none
    stages {
        stage("Build Jar") {
            agent {
                docker {
                    image "maven:eclipse-temurin"
                    args "-u root -v /tmp/m2:/root/.m2"
                }
            }
            steps {
                sh "mvn clean package -DskipTests"
            }
        }
        stage("Build Image") {
            steps {
                script {
                    app = docker.build("stonzim/selenium")
                }
            }
        }
        stage("Push Image") {
            steps {
                script {
                    // registry url is blank for dockerhub
                    docker.withRegistry("", "dockerhub-creds") {
                        app.push("latest")
                    }
                }
            }
        }
    }
}