#!groovy

@Library('github.com/red-panda-ci/jenkins-pipeline-library@v1.4.2') _

// Initialize global condig
cfg = jplConfig('orchextra-sdk', 'android', '', [ hipchat:'', slack:'#integrations', email:'qa+orchextra@gigigo.com' ])

pipeline {
    agent none

    stages {
        stage ('Initialize') {
            agent { label 'docker' }
            steps  {
                jplCheckoutSCM(cfg)
            }
        }
        stage ('Build') {
            agent { label 'docker' }
            steps  {
                jplBuildAPK(cfg,"./gradlew assembleDebug")
            }
        }
        stage ('Instrumentation Test') {
            agent { label 'docker' }
            when { branch 'feature/core_module' }
            steps  {
                // sh 'ci-scripts/bin/doTest.sh'
                echo "Volkswagen Test"
            }
        }
        stage ('Release confirm') {
            when { branch 'release/v*' }
            steps {
                // jplPromoteBuild(cfg)
                echo "Mock release confirm"
            }
        }
        stage ('Release finish') {
            agent { label 'docker' }
            when { branch 'release/v*' }
            steps {
                jplCloseRelease(cfg)
            }
        }
    }

    post {
        always {
            jplPostBuild(cfg)
        }
    }

    options {
        timestamps()
        ansiColor('xterm')
        buildDiscarder(logRotator(artifactNumToKeepStr: '20',artifactDaysToKeepStr: '30'))
        disableConcurrentBuilds()
        skipDefaultCheckout()
        timeout(time: 1, unit: 'DAYS')
    }
}
