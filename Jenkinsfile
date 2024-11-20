pipeline {
    agent {
        kubernetes {
            serviceAccount 'jenkins' // Usamos el ServiceAccount dedicado
            yaml """
apiVersion: v1
kind: Pod
metadata:
  labels:
    jenkins-agent: "true"
spec:
  serviceAccountName: jenkins
  containers:
  - name: maven
    image: maven:3.9.4-eclipse-temurin-21
    command:
      - cat
    tty: true
    volumeMounts:
      - name: maven-cache
        mountPath: /root/.m2
  - name: kaniko
    image: gcr.io/kaniko-project/executor:latest
    args: []
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
  - name: jnlp
    image: jenkins/inbound-agent:latest
    args: '${computer.jnlpMac} ${computer.name}'
    volumeMounts:
      - name: workspace-volume
        mountPath: /home/jenkins/agent
  volumes:
  - name: maven-cache
    persistentVolumeClaim:
      claimName: maven-cache
  - name: docker-config
    secret:
      secretName: docker-credentials
      optional: false
  - name: kubeconfig
    secret:
      secretName: kubeconfig-secret
      optional: false
  - name: workspace-volume
    emptyDir: {}
"""
        }
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials') // ID de las credenciales en Jenkins
        GIT_COMMIT_SHORT = "${env.GIT_COMMIT.take(7)}"
        BUILD_NUMBER = "${env.BUILD_NUMBER}"
        DOCKER_IMAGE = "tu_usuario_docker/tu_nombre_de_imagen:${GIT_COMMIT_SHORT}-${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                container('jnlp') {
                    checkout scm
                }
            }
        }

        stage('Build Maven Project') {
            steps {
                container('maven') {
                    sh 'mvn generate-sources'
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build and Push Docker Image') {
            steps {
                container('kaniko') {
                    script {
                        withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                            // Crear archivo config.json para Kaniko
                            sh '''
                                mkdir -p /kaniko/.docker/
                                echo '{ "auths": { "https://index.docker.io/v1/": { "username": "'${DOCKER_USER}'", "password": "'${DOCKER_PASSWORD}'", "auth": "'$(echo -n ${DOCKER_USER}:${DOCKER_PASSWORD} | base64)'" } } }' > /kaniko/.docker/config.json
                            '''
                            // Ejecutar Kaniko para construir y push la imagen
                            sh """
                                /kaniko/executor --dockerfile=Dockerfile --context=. --destination=${DOCKER_IMAGE} --verbosity=info
                            """
                        }
                    }
                }
            }
        }

        stage('Install envsubst') {
            steps {
                container('kubectl') {
                    sh 'apt-get update && apt-get install -y gettext-base'
                    sh 'envsubst --version' // Verificar instalación
                }
            }
        }

        stage('Preview Deployment Manifest') { // Etapa temporal para verificar (Opcional)
            steps {
                container('kubectl') {
                    configFileProvider([configFile(fileId: 'app-deployment-yaml', variable: 'DEPLOYMENT_YAML')]) { // Usar el ID que asignaste
                        sh """
                            export DOCKER_IMAGE=${DOCKER_IMAGE}
                            envsubst < ${DEPLOYMENT_YAML} > k8s/app-deployment-final.yaml
                            cat k8s/app-deployment-final.yaml
                        """
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                container('kubectl') {
                    configFileProvider([configFile(fileId: 'app-deployment-yaml', variable: 'DEPLOYMENT_YAML')]) { // Usar el ID que asignaste
                        withCredentials([file(credentialsId: 'kubeconfig-secret', variable: 'KUBECONFIG')]) {
                            // Exportar la variable DOCKER_IMAGE para usar en el manifiesto
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

    post {
        always {
            echo 'Pipeline finalizado.'
        }
    }
}
