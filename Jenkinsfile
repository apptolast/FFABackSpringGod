pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "ocholoko888/ffadevback"
        DOCKER_TAG = "${env.BUILD_ID}" // Etiqueta dinámica con el número de build
    }
    stages {
        stage('Preparar fuentes') {
            tools {
                jdk 'OpenJDK-21-ARM64'
            }
            steps {
                sh 'java -version'
                withMaven(maven: 'Maven 3.9.9') {
                    sh 'mvn generate-sources'
                }
            }
        }
        stage('Gestionar Configuración') {
            steps {
                configFileProvider([
                        configFile(fileId: 'firebase-json', targetLocation: 'familyfilmapp-4f3cb-cea8abe4e18b.json'),
                        configFile(fileId: 'application.properties', targetLocation: 'src/main/resources/application.properties'),
                        configFile(fileId: 'app-deployment-yaml', targetLocation: 'app-deployment.yaml'),
                        configFile(fileId: 'kubeconfig', targetLocation: 'kubeconfig')
                ]) {
                    echo 'Archivos de configuración descargados correctamente'
                }
            }
        }
        stage('Depurar') {
            steps {
                sh 'pwd' // Muestra el directorio actual
                sh 'ls -la' // Lista los archivos presentes
            }
        }
        stage('Construir JAR') {
            tools {
                jdk 'OpenJDK-21-ARM64'
            }
            steps {
                withMaven(maven: 'Maven 3.9.9') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }
        stage('Construir imagen Docker') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
            }
        }
        stage('Subir imagen a Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_HUB_USER', passwordVariable: 'DOCKER_HUB_PASS')]) {
                    sh """
                        echo \$DOCKER_HUB_PASS | docker login -u \$DOCKER_HUB_USER --password-stdin
                        docker push ${DOCKER_IMAGE}:${DOCKER_TAG}
                    """
                }
            }
        }
        stage('Actualizar despliegue Kubernetes') {
            steps {
                sh "sed -i 's|ocholoko888/ffadevback:.*|ocholoko888/ffadevback:${DOCKER_TAG}|' app-deployment.yaml"
            }
        }
        stage('Preparar archivos para despliegue') {
            steps {
                stash includes: 'kubeconfig,app-deployment.yaml', name: 'deploy-files'
            }
        }
        stage('Probar conexión Kubernetes') {
            agent {
                kubernetes {
                    yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
    - name: kubectl
      image: lachlanevenson/k8s-kubectl:latest
      command: ['sh', '-c', 'sleep infinity']
"""
                    defaultContainer 'kubectl'
                    workspaceVolume emptyDirWorkspaceVolume()
                }
            }
            steps {
                unstash 'deploy-files'
                sh 'chmod 600 kubeconfig' // Asegura permisos correctos
                sh 'ls -la' // Verifica que los archivos estén presentes
                withEnv(["KUBECONFIG=${env.WORKSPACE}/kubeconfig"]) {
                    sh 'kubectl version --client'
                    sh 'kubectl get nodes'
                }
            }
        }
        stage('Desplegar en Kubernetes') {
            agent {
                kubernetes {
                    yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
    - name: kubectl
      image: lachlanevenson/k8s-kubectl:latest
      command: ['sh', '-c', 'sleep infinity']
"""
                    defaultContainer 'kubectl'
                    workspaceVolume emptyDirWorkspaceVolume()
                }
            }
            steps {
                unstash 'deploy-files'
                sh 'chmod 600 kubeconfig' // Asegura permisos correctos
                sh 'ls -la' // Verifica que los archivos estén presentes
                withEnv(["KUBECONFIG=${env.WORKSPACE}/kubeconfig"]) {
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
