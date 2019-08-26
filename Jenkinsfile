
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

		stage('Building -- Cleaning and compiling Phase') {
      			steps {
				sh 'mvn -f java_project/ clean compile'
      			}
			post {
                		success {
                    			//junit 'java_project/target/surefire-reports/*.xml' 
					echo "Done"
                		}
      			}
     
   		}

                stage('Checking-code-coverage') {
                        steps {
                               sh 'mvn -f java_project/ test verify'
                        }
                        post {
                                success {
                                        //junit 'java_project/target/surefire-reports/*.xml' 
                                        echo "Done"
                                }
                        }
     
                }

		stage('Publishing code on sonar cube for analysis'){
			steps {
			      sh 'mvn -f java_project/ sonar:sonar'
			}

		}
		stage('Deploy') {
			steps {
                               sh 'mvn -X -f java_project/ deploy'
                        }

		}

 	}

}
