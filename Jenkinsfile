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

		stage('Build') {
      			steps {
				script{
					rtMaven.run pom: 'java_project/pom.xml', goals: 'clean install'
				}
      			}
			post {
                		success {
                    			//junit 'java_project/target/surefire-reports/*.xml' 
					echo "Done"
                		}
      			}
     
   		}

		stage('Generate code coverage report'){
			steps {
				script {
                                      rtMaven.run pom: 'java_project/pom.xml', goals: 'test'
				}
			}

		}

 	}

}
