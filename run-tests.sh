#!/bin/bash
# Complete Test Script

echo "🧪 Running all tests..."

echo "1️⃣ Unit tests..."
mvn test

echo "2️⃣ Integration tests..."
mvn verify -P integration-test

echo "3️⃣ Docker tests..."
docker-compose -f docker-compose.test.yml up --abort-on-container-exit

echo "4️⃣ SonarQube analysis..."
mvn sonar:sonar -Dsonar.token=$SONAR_TOKEN

echo "✅ All tests completed!"
