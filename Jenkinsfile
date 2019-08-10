

// ##### Defining the variable
//def server = Artifactory.newServer url: SERVER_URL, credentialsId: CREDENTIALS
def rtMaven = Artifactory.newMavenBuild()
def buildInfo




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
      				rtMaven.tool = 'maven'
			}
   		}

		stage('Build') {
      			steps {
				rtMaven.run pom: 'java_project/pom.xml', goals: 'clean test install'
      			}
			post {
                		success {
                    			junit 'target/surefire-reports/**/*.xml' 
                		}
      			}
     
   		}

 	}

}
