#!groovy

// Build stage
stage ('build') {
  node ('linux') {
    checkout scm
    // Execute gradlew
    sh 'export; pwd; find .'
    sh './gradlew clean build jacocoTestReport jacocoFullReport --stacktrace'
    archive 'sonar-project.properties'
  }
}
