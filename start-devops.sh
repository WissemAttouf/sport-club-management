#!/bin/bash
# Sport Club Management - Startup Script
echo "🚀 Starting Sport Club Management DevOps Stack..."

echo "1️⃣ Building application..."
mvn clean package -DskipTests

echo "2️⃣ Building Docker image..."
docker build -t sport-club-management:latest .

echo "3️⃣ Starting database..."
docker-compose up -d mysql

echo "4️⃣ Starting application..."
docker-compose up -d app

echo "5️⃣ Starting monitoring stack..."
docker-compose -f docker-compose.monitoring.yml up -d

echo "✅ All services started!"
echo "📊 Application: http://localhost:8080"
echo "📈 Prometheus: http://localhost:9090"
echo "📉 Grafana: http://localhost:3000 (admin/admin123)"
echo "📚 Swagger: http://localhost:8080/swagger-ui.html"
