#!groovy

// Build stage
stage ('build') {
  node ('linux') {
    // Execute gradlew
    sh 'export'
    sh 'pwd'
    sh './gradlew clean build jacocoTestReport jacocoFullReport --stacktrace'
    archive 'sonar-project.properties'
  }
}
