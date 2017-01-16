#!groovy

// Build stage
parallel sonarqube : {
  stage ('Sonarqube Analysis') {
    node ('sonarqube') {
      // Get source code
      checkout scm
      // requires SonarQube Scanner 2.8+
      def sonarHome = tool 'SonarQube Runner 2.8';
      withSonarQubeEnv('SonarQube') {
        sh "${sonarHome}/bin/sonar-scanner"
      }
    }
  }
},
build: {
  stage ('Gradle build') {
    node ('linux') {
      // Get source code
      checkout scm
      // Execute gradlew
      sh './gradlew clean build jacocoTestReport jacocoFullReport --stacktrace'
    }
  }
}
