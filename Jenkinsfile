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
                                configFile(fileId: 'app-deployment-yaml', targetLocation: 'app-deployment.yaml'),
                                configFile(fileId: 'kubeconfig', targetLocation: 'admin.conf')
                        ]
                ) {
                    echo 'Archivos de configuración descargados correctamente'
                }
            }
        }
        stage('Depurar') {
            steps {
                script {
                    sh 'pwd' // Muestra el directorio actual
                    sh 'ls -la' // Lista los archivos presentes
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
        stage('Actualizar despliegue Kubernetes') {
            steps {
                script {
                    // Actualizar la imagen en el YAML
                    sh "sed -i 's|ocholoko888/ffadevback:.*|ocholoko888/ffadevback:${DOCKER_TAG}|' app-deployment.yaml"
                }
            }
        }
        stage('Probar conexión Kubernetes') {
            agent {
                kubernetes {
                    cloud 'KubernetesCluster'
                    label 'k8s-agent'
                    defaultContainer 'kubectl'
                    yaml """
            apiVersion: v1
            kind: Pod
            spec:
              containers:
              - name: kubectl
                image: bitnami/kubectl:latest
                command:
                - /bin/sh
                args:
                - -c
                - while true; do sleep 30; done
                tty: true
                env:
                - name: JAVA_OPTS
                  value: "-Djavax.net.ssl.trustStore=/etc/ssl/certs/cacerts -Djavax.net.ssl.trustStorePassword=changeit"
                volumeMounts:
                - name: cacerts-volume
                  mountPath: /etc/ssl/certs
              volumes:
              - name: cacerts-volume
                hostPath:
                  path: /usr/lib/jvm/java-21-openjdk-arm64/lib/security/cacerts
                  type: File
            """
                }
            }
            steps {
                container('kubectl') {
                    script {
                        sh 'kubectl get nodes'
                    }
                }
            }
        }
        stage('Desplegar en Kubernetes') {
            steps {
                withKubeConfig([credentialsId: 'kubeconfig', serverUrl: 'https://23.88.43.3:6443']) {
                    script {
                        sh 'kubectl apply -f app-deployment.yaml'
                    }
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
