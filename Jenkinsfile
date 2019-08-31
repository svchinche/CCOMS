
pipeline {

	agent any


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
				sh 'mvn -f java_project/ -Drevision="${BUILD_NUMBER}" clean'
			}
   		}

		stage('Compiling Phase') {
      			steps {
			    	sh 'mvn -f java_project/ -Drevision="${BUILD_NUMBER}" compile'
      			}
   		}

         	stage('Generate Test Cases using Junit Surefire plugin') {
             		steps {
                 		sh 'mvn -f java_project/ -Drevision="${BUILD_NUMBER}" test'
			}

             		post {
                  		success {
                     			echo "Done"
                  		}
             		}
      		}
		
		stage('Verify code coverage using jacoco') {
                        steps {
                                sh 'mvn -f java_project/ -Drevision="${BUILD_NUMBER}" verify'
                        }

                        post {
                                success {
                                        echo "Done"
                                }       
                        }       
                }

		stage('Publishing code on sonar cube for analysis and send code analysis report using jacoco dataset'){
		    when {
                  	branch 'develop'
            	    }
			steps {
			      sh 'mvn -f java_project/ -Drevision="${BUILD_NUMBER}-SNAPSHOT" sonar:sonar'
			}
		}



		stage('Pushing artifacts to nexux repo - deploy') {

		    when {
			anyOf { 
				branch 'develop'
				allOf {
					//tag "release-*" 
					branch "Feature-*" 
			  	}
			}

		    }
			steps {
                    		sh 'mvn -f java_project/ -Drevision="${BUILD_NUMBER}-SNAPSHOT" deploy'
			}
 		   }
		
		  stage('Downloading artifact for deployment') {

                        steps {
				echo "Downloading ...."
                        }
                  }

		  stage('Installing/Updating the artifact on server QA or Staging or DEV/UAT') {

                        steps {
                                echo "Installation is in Progress ...."
                        }
                  }     
		
		  parallel{

		  stage('Integration Setting is progress') {

                        steps {
                                echo "Integration test is in Progress ...."
                        }
                  }     
                       
		  stage('Performance testing on Staging server') {

                        steps {
                                echo "Performance test is in progress ...."
                        }
                  }     
		
   		  stage('Functional Test Phase') {

                        steps {
                                echo "Function test is in progress...."
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
