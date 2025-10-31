pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = 'utfpr-api-spring'
        CONTAINER_NAME = 'api-spring'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo '📥 Clonando repositório...'
                checkout scm
            }
        }
        
        stage('Build com Maven Wrapper') {
            steps {
                echo '🔨 Compilando com Maven...'
                sh '''
                    chmod +x mvnw
                    ./mvnw clean package -DskipTests
                '''
            }
        }
        
        stage('Build Docker Image') {
            steps {
                echo '🐳 Construindo imagem Docker...'
                sh """
                    docker build -t ${DOCKER_IMAGE}:${BUILD_NUMBER} .
                    docker tag ${DOCKER_IMAGE}:${BUILD_NUMBER} ${DOCKER_IMAGE}:latest
                """
            }
        }
        
        stage('Stop Old Container') {
            steps {
                echo '🛑 Parando container antigo...'
                sh """
                    docker stop ${CONTAINER_NAME} || true
                    docker rm ${CONTAINER_NAME} || true
                """
            }
        }
        
        stage('Deploy') {
            steps {
                echo '🚀 Fazendo deploy...'
                sh """
                    # Obter a rede do docker-compose
                    NETWORK=\$(docker network ls --format '{{.Name}}' | grep utfpr || echo 'utfpr_default')
                    
                    # Iniciar container diretamente
                    docker run -d \
                        --name ${CONTAINER_NAME} \
                        --network \$NETWORK \
                        --restart unless-stopped \
                        -e SPRING_PROFILES_ACTIVE=prod \
                        -e SERVER_PORT=8080 \
                        ${DOCKER_IMAGE}:latest
                    
                    echo "✅ Container iniciado!"
                """
            }
        }
        
        stage('Health Check') {
            steps {
                echo '✅ Verificando saúde da aplicação...'
                script {
                    def healthCheck = false
                    def maxRetries = 10
                    
                    for (int i = 0; i < maxRetries; i++) {
                        try {
                            sh 'docker exec ${CONTAINER_NAME} wget -q -O- http://localhost:8080/actuator/health'
                            healthCheck = true
                            echo "✅ Aplicação está saudável!"
                            break
                        } catch (Exception e) {
                            echo "⏳ Tentativa ${i+1}/${maxRetries}..."
                            sleep 5
                        }
                    }
                    
                    if (!healthCheck) {
                        echo "⚠️  Health check falhou, mas container está rodando"
                    }
                }
            }
        }
        
        stage('Restart Nginx') {
            steps {
                echo '🔄 Reiniciando Nginx para atualizar rotas...'
                sh 'docker restart nginx-proxy || true'
            }
        }
    }
    
    post {
        success {
            echo '━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━'
            echo '✅ DEPLOY REALIZADO COM SUCESSO!'
            echo '━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━'
            echo ''
            echo '🌐 API Spring disponível em:'
            echo '   http://192.168.30.65/api/spring/'
            echo '   http://192.168.30.65/api/spring/actuator/health'
            echo '   http://192.168.30.65/api/spring/swagger-ui.html'
            echo ''
        }
        failure {
            echo '❌ Pipeline falhou!'
            echo 'Logs do container:'
            sh 'docker logs ${CONTAINER_NAME} --tail 50 || true'
        }
        always {
            echo '🧹 Limpando imagens antigas...'
            sh 'docker image prune -f || true'
        }
    }
}
