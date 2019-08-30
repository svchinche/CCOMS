
pipeline {

	agent any


	tools {
        maven 'maven'
        //jdk 'jdk8'
        }

        environment {
        	 APP_NAME = 'pipeline_code'
         	BUILD_NUMBER = "${env.BUILD_NUMBER}"
         	IMAGE_VERSION="v_${BUILD_NUMBER}"
         	GIT_URL="https://github.com/suyogchinche/"
         	SBT_OPTS='-Xmx1024m -Xms512m'
         	JAVA_OPTS='-Xmx1024m -Xms512m'

     	}


	stages {

   		stage('Preparation Phase') {

 			steps {

      			git 'https://github.com/suyogchinche/pipeline_code.git'
      				
			}
   		}

		stage('Building -- Cleaning and compiling Phase') {
      			steps {
			    	sh 'mvn -f java_project/ -Drevision="${BUILD_NUMBER}" clean compile'
      			}
			post {
                		success {

				  	echo "Done"
                			}
      			}
   		}

         	stage('Checking-code-coverage') {
             		steps {
                 		sh 'mvn -f java_project/ -Drevision="${BUILD_NUMBER}" test verify'
			}

             		post {
                  		success {
                     			echo "Done"
                  		}
             		}
      		}

		stage('Publishing code on sonar cube for analysis'){
		    when {
                  	branch 'master'
            	    }
			steps {
			      sh 'mvn -f java_project/ -Drevision="${BUILD_NUMBER}_SNAPSHOT" sonar:sonar'
			}

		}



		stage('Deploy') {

		    when { tag "release-*" }
			steps {
                    		sh 'mvn -f java_project/ -Drevision="${BUILD_NUMBER}" deploy'
			}

 		}
	}
}
