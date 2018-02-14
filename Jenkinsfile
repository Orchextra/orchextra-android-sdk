#!groovy

@Library('github.com/red-panda-ci/jenkins-pipeline-library@v2.6.1') _

// Initialize global condig
cfg = jplConfig('orchextra-sdk', 'android', '', [ hipchat:'', slack:'#integrations', email:'qa+orchextra-android-sdkk@gigigo.com' ])
cfg.androidPackages = '"build-tools;22.0.1" "platforms;android-22" "build-tools;25.0.2" "platforms;android-25" "build-tools;26.0.2" "platforms;android-26"'

pipeline {
    agent none

    stages {
        stage ('Initialize') {
            agent { label 'docker' }
            steps  {
                jplStart(cfg)
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
            when { expression { env.BRANCH_NAME.startsWith('release/v') || env.BRANCH_NAME.startsWith('hotfix/v') } }
            steps {
                jplPromoteBuild(cfg)
            }
        }
        stage ('Release finish') {
            agent { label 'docker' }
            when { expression { env.BRANCH_NAME.startsWith('release/v') || env.BRANCH_NAME.startsWith('hotfix/v') } }
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
