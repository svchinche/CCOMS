def rtMaven = Artifactory.newMavenBuild()

pipeline {

	agent any

	stages {


   		stage('Preparation') { // for display purposes
 			steps {
      				// Get some code from a GitHub repository
      				git 'https://github.com/suyogchinche/pipeline_code.git'
      				// Get the Maven tool.
      				// ** NOTE: This 'M3' Maven tool must be configured
      				// **       in the global configuration.          
				script{
                                        rtMaven.tool = 'maven'
                                }
 
			}
   		}

		stage('Building -- Clening and compiling') {
      			steps {
				script{
					rtMaven.run pom: 'java_project/pom.xml', goals: 'clean compile'
				}
      			}
			post {
                		success {
                    			//junit 'java_project/target/surefire-reports/*.xml' 
					echo "Done"
                		}
      			}
     
   		}

                stage('Check code covergare') {
                        steps {
                                script{
                                        rtMaven.run pom: 'java_project/pom.xml', goals: 'test verify'
                                }
                        }
                        post {
                                success {
                                        //junit 'java_project/target/surefire-reports/*.xml' 
                                        echo "Done"
                                }
                        }
     
                }

		stage('Verify code on sonar cube'){
			steps {
				script {
                                      rtMaven.run pom: 'java_project/pom.xml', goals: 'sonar:sonar'
				}
			}

		}
		stage('Deploy') {
			steps {
                                script {
                                      rtMaven.run pom: 'java_project/pom.xml', goals: 'deploy'
                                }
                        }

		}

 	}

}
