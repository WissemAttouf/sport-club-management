# 💻 Sport Club Management — Gestion d’un club sportif

Application web de gestion des membres d’un club sportif, développée avec **Java 17 et Spring Boot**. Le projet combine des pages web rendues avec Thymeleaf, une couche de persistance Spring Data JPA et une API REST documentée avec OpenAPI/Swagger.

> **Résumé pour recruteurs :** projet pratique mettant en œuvre le développement backend Java, la gestion des données, la documentation d’API et les tests automatisés. Les fonctionnalités de sécurité doivent être décrites uniquement si elles sont présentes et vérifiables dans le code.

<p align="center"><strong>Java 17 · Spring Boot · Maven · Thymeleaf · Spring Data JPA · H2 · Docker</strong></p>

## 📌 Présentation

L’application centralise les opérations courantes de consultation et de gestion des membres d’un club. Elle constitue également un projet pratique pour travailler sur l’architecture Spring Boot, la persistance JPA, la documentation d’API et l’automatisation des tests.

## ✨ Fonctionnalités

- Page d’accueil et pages de présentation du club.
- Consultation et gestion des membres.
- Recherche de membres par nom.
- Création, consultation, modification et suppression de membres.
- Comptage des membres.
- Documentation de l’API avec OpenAPI et Swagger UI.
- Tests automatisés avec l’écosystème Spring Boot Test.
- Configuration Docker pour construire et exécuter l’application.

## 🚀 Démonstration

| Ressource | Lien |
|---|---|
| Application déployée | [Ouvrir l’application](https://sport-club-management-production-6f45.up.railway.app/) |
| Liste des membres | [GET /api/members](https://sport-club-management-production-6f45.up.railway.app/api/members) |
| Documentation Swagger | [Ouvrir Swagger UI](https://sport-club-management-production-6f45.up.railway.app/swagger-ui.html) |
| Spécification OpenAPI | [Consulter le document JSON](https://sport-club-management-production-6f45.up.railway.app/api-docs) |

> La configuration documentée utilise une base H2 en mémoire. Les données peuvent être réinitialisées au redémarrage de l’application. Une base persistante doit être configurée pour un usage durable.

## 🛠️ Technologies

- **Langage et framework :** Java 17, Spring Boot
- **Web :** Spring Web, Spring MVC, Thymeleaf
- **Persistance :** Spring Data JPA, H2
- **API :** OpenAPI, Swagger UI
- **Build et tests :** Maven, JUnit, Spring Boot Test
- **Déploiement et qualité :** Docker, GitHub Actions, configuration JaCoCo/SonarCloud

## 🗂️ Architecture

```text
sport-club-management/
├── .github/workflows/        # Workflows GitHub Actions
├── src/
│   ├── main/java/com/sportclub/
│   │   ├── controller/       # Contrôleurs MVC et REST
│   │   ├── model/            # Entités JPA
│   │   ├── repository/       # Accès aux données
│   │   └── service/          # Logique métier
│   ├── main/resources/
│   │   └── templates/        # Pages Thymeleaf
│   └── test/                 # Tests automatisés
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## 🔌 Routes principales

| Route | Description |
|---|---|
| `/` | Accueil |
| `/members` | Membres |
| `/about` | Présentation |
| `/activities` | Activités sportives |
| `/contact` | Contact |

## 🔌 API REST

Base de l’API : `https://sport-club-management-production-6f45.up.railway.app/api/members`

| Méthode | Endpoint | Description |
|---|---|---|
| GET | `/api/members` | Lister les membres |
| GET | `/api/members/{id}` | Consulter un membre |
| POST | `/api/members` | Créer un membre |
| PUT | `/api/members/{id}` | Modifier un membre |
| DELETE | `/api/members/{id}` | Supprimer un membre |
| GET | `/api/members/search?name={nom}` | Rechercher par nom |
| GET | `/api/members/count` | Compter les membres |

Exemple de consultation :

```bash
curl https://sport-club-management-production-6f45.up.railway.app/api/members
```

## ⚙️ Installation locale

### ⚙️ Prérequis
- JDK 17
- Maven (ou le Maven Wrapper s’il est présent)
- Git

### ⚙️ Lancer le projet

```bash
git clone https://github.com/WissemAttouf/sport-club-management.git
cd sport-club-management
mvn spring-boot:run
```

Consultez la configuration du projet pour connaître le port et les paramètres actifs. Pour exécuter les tests :

```bash
mvn test
```


## 📸 Captures d’écran

<p align="center">
  <img src="docs/images/sport.PNG" alt="Aperçu principal — sport-club-management" width="800">
  <br><strong>Aperçu principal</strong>
  <br><em>Vue d’ensemble de l’interface de Sport Club Management.</em>
</p>

<p align="center">
  <img src="docs/images/sport2.PNG" alt="Gestion du club — sport-club-management" width="800">
  <br><strong>Gestion du club</strong>
  <br><em>Vue complémentaire de l’application de gestion du club sportif.</em>
</p>

<p align="center">
  <img src="docs/images/sport3.PNG" alt="Vue complémentaire — sport-club-management" width="800">
  <br><strong>Vue complémentaire</strong>
  <br><em>Troisième aperçu de l’interface du projet.</em>
</p>

---

## 👨‍💻 Auteur

**Wissem Attouf** — Ingénieur logiciel | Développeur Full Stack  
[GitHub](https://github.com/WissemAttouf) · [LinkedIn](https://www.linkedin.com/in/wissem-attouf-452509226/) · [Portfolio](https://wissem-portfolio.vercel.app/) · [E-mail](mailto:wattouf35@gmail.com)

---

## 🐳 Docker, Kubernetes et CI/CD

Le dépôt comprend une configuration de développement pour exécuter l'application avec MySQL, ainsi que des manifests Kubernetes pour un cluster local Docker Desktop.

### Docker Compose

1. Copier `.env.example` vers `.env` et remplacer les valeurs d'exemple par des mots de passe forts.
2. Construire et démarrer les services :

```bash
docker compose up --build -d
docker compose ps
docker compose logs -f app
```

L'application est disponible sur [http://localhost:8080](http://localhost:8080). Pour arrêter les services sans supprimer les données MySQL :

```bash
docker compose down
```

Pour supprimer également le volume de données, utiliser `docker compose down -v` uniquement si la perte des données est voulue.

### Kubernetes local avec Docker Desktop

Activer Kubernetes dans Docker Desktop et vérifier le contexte :

```bash
kubectl config current-context
kubectl get nodes
docker build -t sport-club-management:dev .
```

Créer localement `k8s/secret.yaml` à partir de `k8s/secret.example.yaml`, remplacer les valeurs fictives par des mots de passe forts, puis appliquer les ressources :

```bash
kubectl apply -f k8s/secret.yaml
kubectl apply -f k8s/mysql.yaml
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
kubectl rollout status statefulset/sport-club-db
kubectl rollout status deployment/sport-club-management
kubectl get pods,svc,pvc
```

L'application est exposée localement sur [http://localhost:30080](http://localhost:30080). Pour consulter les logs :

```bash
kubectl logs deployment/sport-club-management
kubectl logs statefulset/sport-club-db
```

> **Sécurité :** ne committez jamais `.env` ni `k8s/secret.yaml`. Le fichier `k8s/secret.example.yaml` est un modèle, pas un secret prêt à appliquer. Les secrets Kubernetes standards sont encodés, pas chiffrés par défaut ; pour un environnement partagé ou de production, utilisez un gestionnaire de secrets et configurez le chiffrement au repos.

### GitHub Actions

- `.github/workflows/ci.yml` exécute les tests Maven, construit l'image Docker et vérifie la syntaxe YAML des manifests Kubernetes.
- `.github/workflows/deploy.yml` vérifie séparément les manifests Kubernetes.
- Le déploiement sur un cluster distant n'est volontairement pas automatisé tant que le cluster cible, les permissions et la stratégie de gestion des secrets ne sont pas configurés.

Les exécutions et leurs résultats sont visibles dans l'onglet **Actions** du dépôt GitHub.

