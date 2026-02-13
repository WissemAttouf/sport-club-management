#!/bin/bash
# Script d'application Kubernetes

echo "🚀 Applying Kubernetes manifests..."

# Appliquer dans l'ordre
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/secret.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml

# Vérifier le déploiement
echo "📊 Checking deployment status..."
kubectl get pods -l app=sport-club
kubectl get services sport-club-service
kubectl get ingress sport-club-ingress

# Afficher les logs
echo "📋 Pods logs:"
kubectl logs -l app=sport-club --tail=10

echo "✅ Kubernetes deployment complete!"
