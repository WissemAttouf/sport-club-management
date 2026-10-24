
#!/usr/bin/env bash
set -Eeuo pipefail

echo "Deploying Sport Club Management to Kubernetes..."

# Verify required local files
for file in \
  k8s/secret.yaml \
  k8s/mysql.yaml \
  k8s/deployment.yaml \
  k8s/service.yaml
do
  if [[ ! -f "$file" ]]; then
    echo "ERROR: Required file not found: $file" >&2
    exit 1
  fi
done

# Use the Secret locally; never commit secret.yaml
kubectl apply -f k8s/secret.yaml

# Start MySQL before the application
kubectl apply -f k8s/mysql.yaml
kubectl rollout status statefulset/sport-club-db --timeout=180s

# Deploy the application and expose its service
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
kubectl rollout status deployment/sport-club-management --timeout=180s

# Show deployment status
kubectl get pods
kubectl get services sport-club-db sport-club-service

echo "Application logs:"
kubectl logs deployment/sport-club-management --tail=20

echo "Kubernetes deployment completed successfully."
