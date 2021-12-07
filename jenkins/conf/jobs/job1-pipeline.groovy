#!groovy

pipeline {
    agent any
    tools {
        maven 'maven'
    }
    environment {
        TEST = 'TEST'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '100'))
        ansiColor('xterm')
    }
    stages {
        stage('Description') {
            steps {
                script {
                    println('Env var: ' + env.TEST)
                    sh 'java --version'
                    sh 'mvn --version'
                    sh 'python3 --version'
                    currentBuild.displayName = "#${BUILD_NUMBER} ${params.PARAM1}"
                }
            }
        }
        stage('Clone Repo') {
            steps {
                git branch: "${params.BRANCH}",
                        url: 'https://github.com/Ozz007/sb3t.git'
            }
        }
        stage('Compilation') {
            steps {
                sh 'mvn compile'
            }
        }
        stage('Test Unit') {
            when{
                expression{ params.SKIP_TESTS == false }
            }
            steps {
                sh 'mvn test'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Move Jar File'){
            steps {
                sh "mv sb3t-ws/target/sb3t-ws-1.0-SNAPSHOT.jar SB3T-${params.VERSION}-${params.VERSION_TYPE}.jar"
            }
        }
        stage('Test Integration') {
            when{
                expression{ params.SKIP_TESTS == false }
            }
            steps {
                sh 'mvn verify'
            }
        }
    }
}