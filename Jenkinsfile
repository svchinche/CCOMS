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
        SBT_OPTS='-Xmx1024m -Xms512m'
        JAVA_OPTS='-Xmx1024m -Xms512m'
    }
    
    stages {
        stage('Clean-Phase') {
            steps {
                script {
                    if ( BRANCH_NAME == 'master' ) {
                        env.ENVIRONMENT = "uat"
                        env.ENVID= "1"
                    } else if ( BRANCH_NAME == 'hotfix' ){
                        env.ENVIRONMENT = "uat-2"
                        env.ENVID= "2"
                    } else if ( BRANCH_NAME == 'release' ){
                        env.ENVIRONMENT = "qa"
                        env.ENVID= "3"
                    } else if ( BRANCH_NAME == 'develop' ){
                        env.ENVIRONMENT = "dev"
                        env.ENVID= "4"
                    } else {
                        env.ENVIRONMENT = "none"
                        env.ENVID= "5"
                    }
                    env.REVISION_ID = getBuildVersion()
                    env.REVISION="${REVISION_ID}.${ENVID}.${BUILD_NUMBER}"
                }
                sh 'mvn -f ${APP_ROOT_DIR}/pom.xml -Drevision="${REVISION}" clean:clean'
            }
            post {
                failure {
                    emailextrecipients([developers(), upstreamDevelopers(), culprits()])
                }
            }
        }
               
        stage('Gen-Cucumber-Report&verify-jacoco'){
            steps {
                sh 'mvn -f ${APP_ROOT_DIR}/pom.xml -T 4 -Drevision="${REVISION}" jacoco:prepare-agent surefire-report:report jacoco:report jacoco:check@jacoco-check'
            }
            post {
                success {
                    cucumber failedFeaturesNumber: -1, failedScenariosNumber: -1, failedStepsNumber: -1, fileIncludePattern: 'organization-management-system/**/*.json', pendingStepsNumber: -1, skippedStepsNumber: -1, sortingMethod: 'ALPHABETICAL', undefinedStepsNumber: -1
                    jacoco inclusionPattern: '**/*.class'
                    
                }
                failure {
                    emailextrecipients([developers(), upstreamDevelopers(), culprits()])
                }
            }	
        }
        
        stage('Upload-Code-SonarQube'){
            when {
                anyOf {
                    branch 'release'
                }
            }
            steps {
                sh 'mvn -f ${APP_ROOT_DIR}/pom.xml -Drevision="${REVISION}" sonar:sonar'
            }
            post {
                failure {
                    emailextrecipients([developers(), upstreamDevelopers(), culprits()])
                }
            }	
        }
        
        stage('Build&Push-DockerImg') {
            when {
                anyOf {
					branch 'hotfix'
					branch 'master'
                }
            }
            steps {
                sh 'mvn -f ${APP_ROOT_DIR}/pom.xml -Drevision="${REVISION}" -T 5 -DskipTests=true install'
            }
            post {
                failure {
                    emailextrecipients([developers(), upstreamDevelopers(), culprits()])
                }
            }				
        }
		
		stage('Build&Push-DockerImg-SNAPSHOT') {
            when {
                anyOf {
                    branch 'develop'
                    branch 'release'
                }
            }
            steps {
                sh 'mvn -f ${APP_ROOT_DIR}/pom.xml -Drevision="${REVISION}" -T 5 -DskipTests=true install'
            }
            post {
                failure {
                    emailextrecipients([developers(), upstreamDevelopers(), culprits()])
                }
            }	
        }

        stage('Depoloy-PodOnK8S-Cluster') {
            when {
                anyOf {
                    branch 'develop'
                    branch 'release'
                    branch 'hotfix'
                    branch 'master'
                }
            }
            environment {
                ANSIBLE_CONFIG = "$WORKSPACE/kubernetes/ansible_k8s-ccoms-deployment/ansible.cfg"
            }
            steps {
                sh 'kubernetes/ansible_k8s-ccoms-deployment/prereq_verification_ccoms.sh'
                ansiblePlaybook extras: '-e ccoms_service_tag="${REVISION}"', installation: 'ansible_2.8.5',  inventory: 'kubernetes/ansible_k8s-ccoms-deployment/environments/${ENVIRONMENT}', playbook: 'kubernetes/ansible_k8s-ccoms-deployment/ccoms_playbook.yaml'
            }
            post {
                failure {
                    emailextrecipients([developers(), upstreamDevelopers(), culprits()])
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

        stage('Checklist report generation'){
            steps {
                echo "Generating checklist report"
            }
        }
        
    }
}
