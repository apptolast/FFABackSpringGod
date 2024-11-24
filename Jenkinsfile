pipeline {
    agent any
    tools {
        jdk 'OpenJDK-21-ARM64'
    }
    environment {
        DOCKER_HUB_CREDENTIALS = credentials('dockerhub-credentials') // Credenciales de Docker Hub
        KUBECONFIG_CREDENTIALS = credentials('kubeconfig-secret') // Archivo kubeconfig
        DOCKER_IMAGE = "ocholoko888/ffadevback"
        DOCKER_TAG = "latest"
    }
    stages {
        stage('Preparar fuentes') {
            steps {
                script {
                    sh 'java -version'
                    withMaven(maven: 'Maven 3.9.9') {
                        sh 'mvn generate-sources'
                    }
                }
            }
        }
        stage('Gestionar Configuración') {
            steps {
                configFileProvider(
                    [
                        configFile(fileId: 'firebase-json', targetLocation: 'familyfilmapp-4f3cb-cea8abe4e18b.json'),
                        configFile(fileId: 'application.properties', targetLocation: 'src/main/resources/application.properties'),
                        configFile(fileId: 'app-deployment-yaml', targetLocation: 'app-deployment.yaml')
                    ]
                ) {
                    echo 'Archivos de configuración descargados correctamente'
                }
            }
        }
        stage('Construir JAR') {
            steps {
                script {
                    withMaven(maven: 'Maven 3.9.9') {
                        sh 'mvn clean package -DskipTests'
                    }
                }
            }
        }
        stage('Construir imagen Docker') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                }
            }
        }
        stage('Subir imagen a Docker Hub') {
            steps {
                script {
                    sh "echo ${DOCKER_HUB_CREDENTIALS_PSW} | docker login -u ${DOCKER_HUB_CREDENTIALS_USR} --password-stdin"
                    sh "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
                }
            }
        }
        stage('Desplegar en Kubernetes') {
            steps {
                script {
                    sh """
                    kubectl apply -f app-deployment.yaml --kubeconfig=${KUBECONFIG_CREDENTIALS}
                    """
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
