pipeline {
    agent any
    tools {
        jdk 'OpenJDK-21-ARM64'
    }
    environment {
        DOCKER_HUB_CREDENTIALS = credentials('dockerhub-credentials') // Credenciales de Docker Hub
        DOCKER_IMAGE = "ocholoko888/ffadevback"
        DOCKER_TAG = "${env.BUILD_ID}" // Etiqueta dinámica con el número de build
    }
    stages {
        stage('Probar conexión Kubernetes') {
            agent {
                kubernetes {
                    yaml '''
        apiVersion: v1
        kind: Pod
        spec:
          containers:
          - name: kubectl
            image: bitnami/kubectl:latest
            command:
            - cat
            tty: true
        '''
                }
            }
            steps {
                container('kubectl') {
                    sh 'kubectl get nodes'
                }
            }
        }
        stage('Desplegar en Kubernetes') {
            agent {
                kubernetes {
                    yaml '''
        apiVersion: v1
        kind: Pod
        spec:
          containers:
          - name: kubectl
            image: bitnami/kubectl:latest
            command:
            - cat
            tty: true
        '''
                }
            }
            steps {
                container('kubectl') {
                    sh 'kubectl apply -f app-deployment.yaml'
                }
            }
        }
    }
    post {
        success {
            echo 'CI/CD completado exitosamente'
        }
        failure {
            echo 'El pipeline falló'
        }
    }
}
