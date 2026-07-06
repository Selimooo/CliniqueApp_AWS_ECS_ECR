pipeline {
	agent any
	environment {
 
        ECR = "625830750449.dkr.ecr.us-east-1.amazonaws.com/clinic_app"
        BACKEND_IMAGE = "${ECR}/clinic-backend"
        FRONTEND_IMAGE = "${ECR}/clinic-frontend"
        REGISTRY_CREDENTIAL = "ecr:us-east-1:awscreds"
        cluster = "clinic_app"
        service_frontend = "clinicappservice_frontend"
        service_backend = "clinicappservice_backend"
    }

    tools {
	    maven "MAVEN3"
	    jdk "JDK17"
	}
	
	stages{
	    stage('Fetch code') {
            steps {
               git branch: 'main', 
               url: 'git@github.com:Selimooo/ClinicApp_AWS_ECS_ECR.git',
               credentialsId: 'ssh_github'
            }

        }

        stage("Sonar Backend") {
            steps {
                dir('backend') {
                    withSonarQubeEnv('sonarserver') {
                        sh '''
                        mvn clean verify -DskipTests sonar:sonar \
                        -Dsonar.projectKey=clinic-backend \
                        -Dsonar.projectName=clinic-backend \
                        -Dsonar.sources=src/main/java \
                        -Dsonar.tests=src/test/java
                        '''
                    }
                }
            }
        }


        stage("Sonar Frontend") {
            environment {
                scannerHome = tool 'sonar6.2'
            }
            steps {
                dir('clinic_frontend') {
                    withSonarQubeEnv('sonarserver') {
                        sh '''
                        ${scannerHome}/bin/sonar-scanner \
                        -Dsonar.projectKey=clinic-frontend \
                        -Dsonar.projectName=clinic-frontend \
                        -Dsonar.sources=src \
                        -Dsonar.exclusions=node_modules/**,dist/**
                        '''
                    }
                }
            }
        }
        
        stage("Quality Gate") {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        
	    stage('Build Backend Image') {
            steps {
                script {
                    backendImage = docker.build(
                        "${BACKEND_IMAGE}:$BUILD_NUMBER",
                        "./backend"
                    )
                }
            }
        }

        stage('Build Frontend Image') {
            steps {
                script {
                    frontendImage = docker.build(
                        "${FRONTEND_IMAGE}:$BUILD_NUMBER",
                        "./clinic_frontend"
                    )
                }
            }
        }

        

        stage('Push to ECR') {
            steps {
                script {
                    docker.withRegistry("https://${ECR}", REGISTRY_CREDENTIAL) {

                        backendImage.push("latest")
                        backendImage.push("${BUILD_NUMBER}")

                        frontendImage.push("latest")
                        frontendImage.push("${BUILD_NUMBER}")
                    }
                }
            }
        }

       /**  
        stage('Deploy Frontend to ecs') {
            steps {
                withAWS(credentials: 'awscreds', region: 'us-east-1') {

                    sh 'aws ecs update-service --cluster ${cluster} --service ${service_frontend} --force-new-deployment'
                
                }
            }
	   
	    }

        stage('Deploy Backend to ecs') {
            steps {
                withAWS(credentials: 'awscreds', region: 'us-east-1') {

                    sh 'aws ecs update-service --cluster ${cluster} --service ${service_backend} --force-new-deployment'
                
                }
            }
	   
	    }**/

    }

    post {
        success {
            slackSend channel: '#devops_cicd',
                      tokenCredentialId: 'slacktoken',
                      color: 'good',
                      message: "🚀 SUCCESS: ClinicApp deployed successfully ✔️ Build #${BUILD_NUMBER}"
        }

        failure {
            slackSend channel: '#devops_cicd',
                      tokenCredentialId: 'slacktoken',
                      color: 'danger',
                      message: "❌ FAILED: Pipeline failed ⚠️ Build #${BUILD_NUMBER}"
        }
    }
}