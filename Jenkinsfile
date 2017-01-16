#!groovy

// Build stage
stage ('sonarqube') {
  node {
    stage('SCM') {
      checkout scm
    }
    stage('SonarQube analysis') {
      // requires SonarQube Scanner 2.8+
      def scannerHome = tool 'SonarQube Scanner 2.8';
      withSonarQubeEnv('My SonarQube Server') {
        sh "${scannerHome}/bin/sonar-scanner"
      }
    }
  }
}

stage ('build') {
  node ('linux') {
    checkout scm
    // Execute gradlew
    sh './gradlew clean build jacocoTestReport jacocoFullReport --stacktrace'
  }
}
