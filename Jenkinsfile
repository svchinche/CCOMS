pipeline {

    agent {
        label 'master'
    }
    tools {
        maven 'maven'
        jdk 'jdk8'
    }

    libraries {
        lib('git_infoshared_lib@master')
    }

    environment {
         
        APP_NAME = "ccoms"
        APP_ROOT_DIR = "organization-management-system"
        APP_AUTHOR = "Suyog Chinche"
        
        GIT_URL="https://github.com/svchinche/CCOMS.git"

        VERSION_NUMBER=VersionNumber([
            versionNumberString :'${BUILD_MONTH}.${BUILDS_TODAY}.${REVISION_IDBER}',
            projectStartDate : '2019-02-09',
            versionPrefix : 'v'
        ])

        SBT_OPTS='-Xmx1024m -Xms512m'
        JAVA_OPTS='-Xmx1024m -Xms512m'

    }


    stages {
         
        stage('Cleaning Phase') {
            steps {
                 /* This block used here since VERSION_NUMBER env var is not initialize and we were initializing this value through shared library  */
                script {
                    env.REVISION_ID = getBuildVersion()
                }
                sh 'mvn -f ${APP_ROOT_DIR}/pom.xml -Drevision="${REVISION_ID}" clean:clean'
            }
            post {
                failure {
                    mailextrecipients([developers(), upstreamDevelopers(), culprits()])
                }
            }
        }
        
        
        stage('Verify JACOCO & Run Unit Test Cases'){
            steps {
                sh 'mvn -f ${APP_ROOT_DIR}/pom.xml -T 4 -Drevision="${REVISION_ID}" jacoco:prepare-agent surefire-report:report jacoco:report jacoco:check@jacoco-check'
            }
            post {
                success {
                     publishHTML([allowMissing: false, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'organization-management-system/target/site', reportFiles: 'surefire-report.html', reportName: 'CCOMS Unit Test Report', reportTitles: 'CCOMS Unit Test Result'])
                     jacoco buildOverBuild: true, changeBuildStatus: true, inclusionPattern: '**/*.class'
                }
                failure {
                    mailextrecipients([developers(), upstreamDevelopers(), culprits()])
                }
            }	
        }
        
        stage('Publishing code on SONARQUBE'){
            when {
                anyOf {
                    branch 'release'
                }
            }
            steps {
                sh 'mvn -f ${APP_ROOT_DIR}/pom.xml -Drevision="${REVISION_ID}" sonar:sonar'
            }
            post {
                failure {
                    mailextrecipients([developers(), upstreamDevelopers(), culprits()])
                }
            }	
        }
        
		 
        stage('Packaging') {
            steps {
                sh 'mvn -f ${APP_ROOT_DIR}/pom.xml -Drevision="${REVISION_ID}" -T 4 -pl department-service,employee-service,gateway-service,organization-service war:war spring-boot:repackage dependency:unpack@unpack'
            }
            post {
                failure {
                    mailextrecipients([developers(), upstreamDevelopers(), culprits()])
                }
            }	
        }
        
        stage('Building and Pushing an image') {
            when {
                anyOf {
                    branch 'release'
					branch 'hotfix'
					branch 'master'
                }
            }
            steps {
                sh 'mvn -f ${APP_ROOT_DIR}/pom.xml -Drevision="${REVISION_ID}" -T 4 dockerfile:build dockerfile:tag@tag-version dockerfile:push@default dockerfile:push@tag-version'
            }
            post {
                failure {
                    mailextrecipients([developers(), upstreamDevelopers(), culprits()])
                }
            }				
        }
		
		// In case of develop branch, QA env will be provision based on local private docker registry
		stage('Snapshot Release- Building and Pushing an image') {
            when {
                anyOf {
                    branch 'develop'
                }
            }
            steps {
                sh 'mvn -f ${APP_ROOT_DIR}/pom.xml -Drevision="${REVISION_ID}" -T 4 dockerfile:build dockerfile:tag@tag-version dockerfile:push@default dockerfile:push@tag-version'
            }
            post {
                failure {
                    mailextrecipients([developers(), upstreamDevelopers(), culprits()])
                }
            }	
        }

        stage('Install/Update artifact - QA/Stage/DEV-UAT') {
            steps {
                echo "Installation is in Progress ...."
            }
            post {
                failure {
                    mailextrecipients([developers(), upstreamDevelopers(), culprits()])
                }
            }	
        }


        stage('Post Deployment ll stages') {
        
            parallel {
            
                stage('Integration Test') {
                    steps {
                        echo "Integration test is in Progress ...."
                    }
                }
				
                /*
                stage('Performance Test') {

                    environment{
                        JMETER_HOME="/u01/app/jmeter/apache-jmeter-5.1.1";
                        JMX_FILE_LOC="${APP_ROOT_DIR}/jmeter_test_cases/buyer.jmx"
                        JMX_RESULT_FILE_LOC="${APP_ROOT_DIR}/target/result.file"
                        JMX_WEB_REP_LOC="${APP_ROOT_DIR}/target/jmxreport"
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
                        sh 'mvn -f ${APP_ROOT_DIR}/pom.xml -Drevision="${REVISION_ID}" failsafe:integration-test'
                    }
                    post {
                        always {
                            publishHTML([allowMissing: false, alwaysLinkToLastBuild: true, keepAll: true, reportDir: '${APP_ROOT_DIR}/target/failsafe-reports', reportFiles: 'index.html', reportName: 'Integration Test Report', reportTitles: 'IT Test Result'])
                        }
                    }
                }
                */
            }
        }

        stage('Checklist report generation') {
            steps {
                echo "Generating checklist report"
            }
        }
        
    }
}
