pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "ocholoko888/ffadevback"
        DOCKER_TAG = "${env.BUILD_ID}" // Etiqueta dinámica con el número de build
    }
    stages {
        stage('Notificar Inicio') {
            steps {
                script {
                    discordSend description: "🚀 Iniciando Pipeline de FFABackSpringGod",
                            link: env.BUILD_URL,
                            result: currentBuild.currentResult,
                            title: JOB_NAME,
                            webhookURL: credentials('discord-webhook')
                }
            }
        }
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
                        configFile(fileId: 'app-logs-pvc-yaml', targetLocation: 'app-logs-pvc.yaml'),
                        configFile(fileId: 'app-service-yaml', targetLocation: 'app-service.yaml'),
                        configFile(fileId: 'app-configmap-yaml', targetLocation: 'app-configmap.yaml'),
                        configFile(fileId: 'firebase-secret-yaml', targetLocation: 'firebase-secret.yaml'),
                        configFile(fileId: 'log-server-service-yaml', targetLocation: 'log-server-service.yaml'),
                        configFile(fileId: 'log-server-yaml', targetLocation: 'log-server.yaml'),
                        configFile(fileId: 'nginx-configmap-yaml', targetLocation: 'nginx-configmap.yaml'),
                        configFile(fileId: 'postgres-pvc-yaml', targetLocation: 'postgres-pvc.yaml'),
                        configFile(fileId: 'postgres-secret-yaml', targetLocation: 'postgres-secret.yaml'),
                        configFile(fileId: 'postgres-service-yaml', targetLocation: 'postgres-service.yaml'),
                        configFile(fileId: 'postgres-deployment-yaml', targetLocation: 'postgres-deployment.yaml'),
                        configFile(fileId: 'redis-deployment-yaml', targetLocation: 'redis-deployment.yaml'),
                        configFile(fileId: 'redis-pvc-yaml', targetLocation: 'redis-pvc.yaml'),
                        configFile(fileId: 'redis-service-yaml', targetLocation: 'redis-service.yaml'),
                        configFile(fileId: 'kubeconfig', targetLocation: 'kubeconfig')
                ]) {
                    echo 'Archivos de configuración y manifiestos descargados correctamente'
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
                sh "sed -i 's|IMAGE_PLACEHOLDER|${DOCKER_IMAGE}:${DOCKER_TAG}|g' app-deployment.yaml"
                sh 'cat app-deployment.yaml' // Opcional: Muestra el contenido actualizado
            }
        }
        stage('Preparar archivos para despliegue') {
            steps {
                stash includes: 'kubeconfig,*.yaml', name: 'deploy-files'
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
                    sh '''
                kubectl apply -f app-configmap.yaml
                kubectl apply -f nginx-configmap.yaml
                kubectl apply -f firebase-secret.yaml
                kubectl apply -f postgres-secret.yaml
                kubectl apply -f postgres-pvc.yaml
                kubectl apply -f redis-pvc.yaml
                kubectl apply -f app-logs-pvc.yaml
                kubectl apply -f postgres-deployment.yaml
                kubectl apply -f postgres-service.yaml
                kubectl apply -f redis-deployment.yaml
                kubectl apply -f redis-service.yaml
                kubectl apply -f log-server.yaml
                kubectl apply -f log-server-service.yaml
                kubectl apply -f app-deployment.yaml
                kubectl apply -f app-service.yaml
            '''
                }
            }
        }
    }
    post {
        success {
            script {
                discordSend description: "✅ Pipeline completado exitosamente!\nImagen: ${DOCKER_IMAGE}:${DOCKER_TAG}",
                        link: env.BUILD_URL,
                        result: currentBuild.currentResult,
                        title: JOB_NAME,
                        webhookURL: credentials('discord-webhook')
            }
            echo 'CI/CD completado exitosamente'
        }
        failure {
            script {
                discordSend description: "❌ El pipeline falló\nRevisa los logs para más detalles",
                        link: env.BUILD_URL,
                        result: currentBuild.currentResult,
                        title: JOB_NAME,
                        webhookURL: credentials('discord-webhook')
            }
            echo 'El pipeline falló'
        }
    }
}
