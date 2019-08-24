def rtMaven = Artifactory.newMavenBuild()

pipeline {

	agent any
	
	tools {
        maven 'maven'
        //jdk 'jdk8'
        }

	stages {


   		stage('Preparation Phase') { // for display purposes
 			steps {
      				// Get some code from a GitHub repository
      				git 'https://github.com/suyogchinche/pipeline_code.git'
      				
			}
   		}

		stage('Building -- Clening and compiling Phase') {
      			steps {
				sh 'mvn clean compile'
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
                               sh 'mvn test verfify'
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
			      sh 'mvn sonar:sonar'
			}

		}
		stage('Deploy') {
			steps {
                               sh 'mvn -X deploy'
                        }

		}

 	}

}
