pipeline {
      agent master
      tools {
      maven 'maven'
      //jdk 'jdk8'
      }

      environment {
           APP_NAME = 'pipeline_code'
           VERSION_PREFIX = "v1"
           BUILD_NUMBER = "${env.VERSION_PREFIX}.${env.BUILD_ID}.${env.BUILD_NUMBER}"
           GIT_URL="https://github.com/suyogchinche/"
           SBT_OPTS='-Xmx1024m -Xms512m'
           JAVA_OPTS='-Xmx1024m -Xms512m'
      }

      stages {
           stage('Cleaning Phase') {
                steps {
                     sh 'mvn -f java_project/pom.xml -Drevision="${BUILD_NUMBER}" clean:clean'
                }
           }

           stage('Compiling Phase') {
                steps {
                     sh 'mvn -f java_project/pom.xml -Drevision="${BUILD_NUMBER}" compiler:compile'
                }
           }

           stage('Generate Test Cases - Surefire') {
                steps {
                     sh 'mvn -f java_project/pom.xml -Drevision="${BUILD_NUMBER}" surefire:test'
                }

                post {
                     success {
                          junit 'java_project/target/surefire-reports/*.xml'
                     }
                }
           }

           stage('Verify code coverage - Jacoco') {
                steps {
                    sh 'mvn -f java_project/pom.xml -Drevision="${BUILD_NUMBER}" jacoco:prepare-agent jacoco:report jacoco:check@jacoco-check'
                }
                post {
                     success {
                         echo "Done"
                     }
                }
           }
           stage('Publishing code on SONARQUBE'){
                when {
                    branch 'develop'
                }
                steps {
                    sh 'mvn -f java_project/pom.xml -Drevision="${BUILD_NUMBER}-SNAPSHOT" sonar:sonar'
                }
           }
           stage('Pushing artifacts to NEXUS') {
                when {
                     anyOf {
                          branch 'develop'
                                allOf {
                                     tag "release-*"
                                     branch "Feature-*"
                                }
                          }
                     }
                steps {
                     sh 'mvn -f java_project/pom.xml -Drevision="${BUILD_NUMBER}-SNAPSHOT" jar:jar deploy:deploy'
                }
           }

           stage('Download artifact for deployment') {
                steps {
                     echo "Downloading ...."
                }
           }

           stage('Install/Update artifact - QA/Stage/DEV-UAT') {
                steps {
                     echo "Installation is in Progress ...."
                }
           }


           stage('Post Deployment ll stages') {
                parallel {
                     stage('Integration Test') {
                           steps {
                                  echo "Integration test is in Progress ...."
                           }
                     }

                     stage('Performance Test') {
                           steps {
                                 echo "Performance test is in progress ...."
                           }
                     }

                     stage('Functional Test') {
                           steps {
                                 echo "Function test is in progress...."
                           }
                     }
                 }
           }

           stage('Checklist report generation') {
                steps {
                    echo "Generating checklist report"
                }
           }
      }
}
