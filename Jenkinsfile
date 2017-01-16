#!groovy

// Build stage
parallel sonarqube : {
  stage ('sonarqube') {
    node ('sonarqube') {
      sh "export"
      stage('SCM') {
        checkout scm
      }
      stage('SonarQube analysis') {
        // requires SonarQube Scanner 2.8+
        def sonarHome = tool 'SonarQube Runner 2.8';
        echo "${sonarHome}"
        withSonarQubeEnv('SonarQube') {
          sh "export"
          sh "${sonarHome}/bin/sonar-scanner"
        }
      }
    }
  }
},
build: {
  stage ('build') {
    node ('linux') {
      stage('SCM') {
        checkout scm
      }
      // Execute gradlew
      sh './gradlew clean build jacocoTestReport jacocoFullReport --stacktrace'
    }
  }
}
