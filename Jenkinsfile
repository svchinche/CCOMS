pipeline {
      agent {
        label 'master'
      }
      tools {
      maven 'maven'
      jdk 'jdk8'
      }

      environment {
           //application metadata related variables 
           APP_NAME = "quickstart"
           APP_AUTHOR = "Suyog Chinche"
           PACKAGING  = "jar"
           OUTPUT_FILE = "${APP_NAME}.${PACKAGING}" 
           MAVEN_EXTENSION = "${PACKAGING}"
           SORT_OPTION = "repository"
           GIT_URL="https://github.com/suyogchinche/CICD_declarative_pipeline.git"

           // Version specific variable
           VERSION_NUMBER=VersionNumber([
               versionNumberString :'${BUILD_MONTH}.${BUILDS_TODAY}.${BUILD_NUMBER}',
               projectStartDate : '2019-02-09',
               versionPrefix : 'v'
           ])

           //appn memory argument
           SBT_OPTS='-Xmx1024m -Xms512m'
           JAVA_OPTS='-Xmx1024m -Xms512m'

           //All servers URL
           NEXUS_URL = "worker-node1:8081"
      }

      stages {

           stage('Preparation phase') {
                when { anyOf { branch 'develop'; branch 'Feature*' ; tag 'release*' } }
                steps{
                    script{
                        // Created new environment variable as environment varible value is immutable, through environment directive it updates but not persist its value across the stages
                         env.BUILD_NUM = "${env.VERSION_NUMBER}-SNAPSHOT"
                     }
                }
           }
        
           stage('Cleaning Phase') {
                steps {
                     //script {
                     //    env.BUILD_NUM = "${env.VERSION_NUMBER}"
                     //}
                     sh 'mvn -f java_project/pom.xml -Drevision="${BUILD_NUM}" clean:clean'
                }
           }
           
           // Compile main and test classes
           stage('Compiling Phase') {
                steps {
                     sh 'mvn -f java_project/pom.xml -Drevision="${BUILD_NUM}" compiler:compile compiler:testCompile'
                }
           }

           // Generate test cases using default surefire plugin in maven
           stage('Generate Test Cases - Surefire') {
                steps {
                     sh 'mvn -f java_project/pom.xml -Drevision="${BUILD_NUM}" surefire:test'
                }

                post {
                     success {
                          junit 'java_project/target/surefire-reports/*.xml'
                     }
                }
           }
          
           stage('Verify code coverage - Jacoco') {
                steps {
                    sh 'mvn -f java_project/pom.xml -Drevision="${BUILD_NUM}" jacoco:prepare-agent surefire:test jacoco:report jacoco:check@jacoco-check'
                }
                post {
                     success {
                         echo "Done"
                     }
                }
           }
    
           stage('Publishing code on SONARQUBE'){
                when {
                     anyOf {
                          branch 'develop'
                          branch 'release'
                     }
                }
                steps {
                    sh 'mvn -f java_project/pom.xml -Drevision="${BUILD_NUM}" sonar:sonar'
                }
           }
           stage('Pushing artifacts to NEXUS') {
                when {
                     anyOf {
                          branch 'develop'
                          branch 'release'
                          allOf {
                                     // branch is not required .## at a time branch or tag works in multibranching project
                                     //branch "Feature-*"
                                     tag "release-*"
                          }
                     }
                }
                steps {
                     sh 'mvn -f java_project/pom.xml -Drevision="${BUILD_NUM}" jar:jar deploy:deploy'
                }
           }

           stage('Download artifact for deployment') {
                environment{
                       ASSET_URL= "http://${NEXUS_URL}/service/rest/v1/search/assets/download?sort=${SORT_OPTION}&repository=maven-snapshots&name=${APP_NAME}&group=com.vogella.maven&version=${VERSION_NUMBER}&maven.extension=${PACKAGING}"
                }
                steps {
                     echo "Downloading ...."
                     withCredentials([usernamePassword(credentialsId: 'nexus_artifactory_repository_credentials', passwordVariable: 'password', usernameVariable: 'username')]) { 
                         sh 'curl -u ${username}:${password} -L -X GET ${ASSET_URL} -o ${APP_NAME}.${PACKAGING}'
                     }
                      
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

                          environment{
                                JMETER_HOME="/u01/app/jmeter/apache-jmeter-5.1.1";
                                JMX_FILE_LOC="java_project/jmeter_test_cases/buyer.jmx"
                                JMX_RESULT_FILE_LOC="java_project/target/result.file"
                                JMX_WEB_REP_LOC="java_project/target/jmxreport"
                            }

                           steps {
                                 echo "Performance test is in progress ...."
                                 sh script: ''' [ -d ${JMX_WEB_REP_LOC} ] &&  rm -rf  ${JMX_WEB_REP_LOC}
                                                [ -f ${JMX_RESULT_FILE_LOC} ] &&  rm -rf  ${JMX_RESULT_FILE_LOC}
                                                mkdir -p ${JMX_WEB_REP_LOC}
                                                ${JMETER_HOME}/bin/jmeter.sh -n -t ${JMX_FILE_LOC} -l ${JMX_RESULT_FILE_LOC} -e -o ${JMX_WEB_REP_LOC}
                                            '''
                           }
                           post {
                               success {
                                   perfReport sourceDataFiles: env.JMX_RESULT_FILE_LOC
                               }
                           }

                     }

                     stage('Functional Regression Test') {
                           steps {
                                 echo "Function test is in progress...."
                                 sh 'mvn -f java_project/ -Drevision="${BUILD_NUM}" failsafe:integration-test'
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
