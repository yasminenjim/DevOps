pipeline {
    agent any
   tools {



        jdk 'jdk'
        maven 'maven'
    }
    stages {
        stage('clone git repo'){
            steps {
                bat "if exist Timesheet_DevOps rmdir /s /q Timesheet_DevOps"
                bat "git clone -b yasmine https://github.com/Halilaw/Timesheet_DevOps.git"
            }
        }
		
		
        
		
          stage('sonar'){
            steps {
                dir("Timesheet_DevOps"){
                    bat "mvn sonar:sonar"
                }
            }
        }
        
         stage("mvn test") {
            steps {
                dir("Timesheet_DevOps"){
                     bat "mvn test"
                }
            }
		 
		 
        }
       
       
        
       
     stage("mvn package") {
            steps {
                dir("Timesheet_DevOps"){
                    bat "mvn package -Dmaven.test.skip"
                }
            }
        }
        stage("Deployment stage") {
            steps {
                dir('Timesheet_DevOps') {
                    bat "mvn deploy"
                }
            }
        }

        
          
         
         
        stage('Building docker image') {
          steps{
                dir("Timesheet_DevOps") {
                   bat "docker build -t devops ."
			
			bat "docker tag devops  11815357/devops:1"
			bat"docker login -u 11815357 -p 193JFT5055"
			bat "docker push  11815357/devops:1"
                }
            }    
        }
            

       
    }
    
    
 post { 
        
    
        always { 
            echo 'Im sending an email!'
            emailext (to: 'yasminenjim42@gmail.com',
            replyTo: 'yasminenjim42@gmail.com',
            subject: "Email Report from - '${env.JOB_NAME}' ",
            attachLog:true,
            body: readFile("C:/Program Files (x86)/Jenkins/workspace/pipeline_devops/Timesheet_DevOps/contrat.txt"));
        }
    }   
    
    
    
}


 
