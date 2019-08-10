pipeline
{
    agent {
        label 'master'
    }
    stages {
        
        stage("first stage") {
            steps {
                echo 'Hello World'
                sh 'pwd'
                sh 'printenv'
                sh 'id'
                sh 'uname -a'
                sh 'cd /u01/app'
                sh 'pwd'
           }
       }
       
        stage("Second stage") {
            steps {
                echo "Second stage"
            }
        }
        
    }
}
