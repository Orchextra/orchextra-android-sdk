#!groovy

@Library('github.com/pedroamador/jenkins-pipeline-library@develop') _

// Initialize global condig
cfg = jplConfig('', 'android', '', [ hipchat:'', slack:'#integrations', email:'qa+orchextra@gigigo.com' ])

pipeline {
    agent none

    stages {
        stage ('Build') {
            agent { label 'docker' }
            steps  {
                jplCheckoutSCM(cfg)
                jplBuildAPK(cfg)
            }
        }
        stage ('Instrumentation Test') {
            agent { label 'docker' }
            when { branch 'feature/core_module' }
            steps  {
                timestamps {
                    ansiColor('xterm') {
                        sh 'ci-scripts/bin/doTest.sh'
                    }
                }
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
        buildDiscarder(logRotator(artifactNumToKeepStr: '20',artifactDaysToKeepStr: '30'))
        disableConcurrentBuilds()
        skipDefaultCheckout()
        timeout(time: 1, unit: 'DAYS')
    }
}
