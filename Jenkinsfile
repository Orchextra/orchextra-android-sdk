#!groovy

@Library('github.com/red-panda-ci/jenkins-pipeline-library@develop') _

// Initialize global condig
cfg = jplConfig('orchextra-sdk', 'android', '', [ hipchat:'', slack:'#integrations', email:'qa+orchextra@gigigo.com' ])

pipeline {
    agent none

    stages {
        stage ('Build') {
            agent { label 'docker' }
            steps  {
                jplCheckoutSCM(cfg)
                jplBuildAPK(cfg,"gradlew assembleDebug")
            }
        }
        stage ('Instrumentation Test') {
            agent { label 'docker' }
            when { branch 'feature/core_module' }
            steps  {
                // sh 'ci-scripts/bin/doTest.sh'
                echo "Disable interface test temporally"
            }
        }
        stage ('Release confirm') {
            when { branch 'release/*' }
            steps {
                // jplPromoteBuild(cfg)
                echo "Mock release confirm"
            }
        }
        stage ('Release finish') {
            agent { label 'docker' }
            when { expression { env.BRANCH_NAME.startsWith('release/') && cfg.promoteBuild } }
            steps {
                //jplCloseRelease(cfg)
                //deleteDir()
                echo "Mock release finish"
            }
        }
        stage ('PR Clean') {
            agent { label 'docker' }
            when { branch 'PR-*' }
            steps {
                deleteDir();
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
