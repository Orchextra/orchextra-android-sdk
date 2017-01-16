#!groovy

// Build stage
stage ('build') {
  node ('linux') {
    checkout scm
    // Execute gradlew
    sh './gradlew clean build jacocoTestReport jacocoFullReport --stacktrace'
    archive 'sonar-project.properties'
  }
}
