pipeline {
    agent {
        kubernetes {
            cloud 'kubernetes'
            yaml '''
apiVersion: v1
kind: Pod
metadata:
  labels:
    jenkins: agent
spec:
  containers:
  - name: maven
    image: maven:3.9.4-eclipse-temurin-21
    command:
    - cat
    tty: true
    volumeMounts:
      - name: m2
        mountPath: /root/.m2
  - name: kaniko
    image: gcr.io/kaniko-project/executor:debug
    command:
    - cat
    tty: true
    volumeMounts:
      - name: docker-config
        mountPath: /kaniko/.docker/
  - name: kubectl
    image: bitnami/kubectl:latest
    command:
    - cat
    tty: true
    volumeMounts:
      - name: kubeconfig
        mountPath: /root/.kube/
  volumes:
  - name: docker-config
    secret:
      secretName: docker-credentials
      items:
        - key: .dockerconfigjson
          path: config.json
  - name: kubeconfig
    secret:
      secretName: kubeconfig-secret
  - name: m2
    persistentVolumeClaim:
      claimName: maven-cache
'''
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
                                /kaniko/executor \
                                    --dockerfile=Dockerfile \
                                    --context=. \
                                    --destination=${DOCKER_IMAGE} \
                                    --verbosity=info
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
                                kubectl rollout status deployment/app
                            """
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}