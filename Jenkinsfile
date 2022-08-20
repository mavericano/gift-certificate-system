pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/jenkins']], extensions: [], userRemoteConfigs: [[credentialsId: 'github-ssh-key', url: 'git@github.com:mavericano/gift-certificate-system.git']]])
                sh 'mvn clean package'
            }

            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts '**/target/*.jar'
                }
            }
        }
        stage('Scan') {
            steps {
                withSonarQubeEnv(installationName: 'sonar') {
                    sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar'
                }
            }
        }
        stage('Deploy') {
            steps {
                deploy adapters: [tomcat9(credentialsId: 'jenkins to tomcat', path: '', url: 'http://localhost:8082')], contextPath: '/gift-certificate-rest', onFailure: false, war: '**/*.war'
            }
        }
    }
}