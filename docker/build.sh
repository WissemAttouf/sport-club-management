#!/bin/bash
# Script de build Docker optimisé

echo "🔨 Building Sport Club Management Docker image..."

# Build l'image
docker build -t sport-club-management:latest .

# Tag pour différentes environnements
docker tag sport-club-management:latest sport-club-management:staging
docker tag sport-club-management:latest sport-club-management:$BUILD_NUMBER

# Liste les images créées
echo "✅ Images créées :"
docker images | grep sport-club-management
