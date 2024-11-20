pipeline {
    agent {
        kubernetes {
            yamlFile 'app-deployment-yaml'  // Este es el ID del archivo de configuración en Jenkins
        }
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials')
        GIT_COMMIT_SHORT = "${env.GIT_COMMIT[0..7]}"
        BUILD_NUMBER = "${env.BUILD_NUMBER}"
        DOCKER_IMAGE = "ocholoko888/ffadevback:${GIT_COMMIT_SHORT}-${BUILD_NUMBER}"
    }

    stages {
        stage('Build Maven Project') {
            steps {
                container('maven') {
                    sh '''
                        mvn generate-sources
                        mvn clean package -DskipTests
                    '''
                }
            }
        }

        stage('Build and Push Docker Image') {
            steps {
                container('kaniko') {
                    script {
                        withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                            sh '''
                                mkdir -p /kaniko/.docker/
                                echo '{ "auths": { "https://index.docker.io/v1/": { "username": "'${DOCKER_USER}'", "password": "'${DOCKER_PASSWORD}'", "auth": "'$(echo -n ${DOCKER_USER}:${DOCKER_PASSWORD} | base64)'" } } }' > /kaniko/.docker/config.json
                            '''
                            sh """
                                /kaniko/executor --dockerfile=Dockerfile --context=. --destination=${DOCKER_IMAGE} --verbosity=info
                            """
                        }
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                container('kubectl') {
                    configFileProvider([configFile(fileId: 'app-deployment-yaml', variable: 'DEPLOYMENT_YAML')]) {
                        withCredentials([file(credentialsId: 'kubeconfig-secret', variable: 'KUBECONFIG')]) {
                            sh """
                                export DOCKER_IMAGE=${DOCKER_IMAGE}
                                envsubst < ${DEPLOYMENT_YAML} | kubectl apply -f -
                            """
                        }
                    }
                }
            }
        }
    }
}