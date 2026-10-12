# 🏟️ Sport Club Management

Application web de gestion d’un club sportif, développée avec **Java 17 et Spring Boot**. Le projet propose un site web rendu côté serveur avec Thymeleaf, une API REST pour gérer les membres, une documentation OpenAPI/Swagger et une configuration Docker.

<p align="center">
  <strong>Spring Boot · Java 17 · Maven · Thymeleaf · Spring Data JPA · H2 · Docker</strong>
</p>

## 📌 Présentation

Sport Club Management centralise les principales opérations liées aux membres d’un club sportif et présente les informations du club à travers plusieurs pages web.

### Fonctionnalités

- 🏠 Page d’accueil et pages de présentation du club.
- 👥 Consultation et gestion des membres via une API REST.
- 🔎 Recherche de membres par nom.
- 🔢 Comptage du nombre de membres.
- 🧾 Création, consultation, modification et suppression des membres.
- 📚 Documentation de l’API avec OpenAPI et Swagger UI.
- 🧪 Tests automatisés avec l’écosystème de test Spring Boot.
- 🐳 Image Docker multi-stage pour construire et exécuter l’application.

## 🖥️ Démo en ligne

| Ressource | Lien |
|---|---|
| Application déployée sur Railway | [Ouvrir l’application](https://sport-club-management-production-6f45.up.railway.app/) |
| API — liste des membres | [GET /api/members](https://sport-club-management-production-6f45.up.railway.app/api/members) |
| Documentation Swagger UI | [Ouvrir Swagger](https://sport-club-management-production-6f45.up.railway.app/swagger-ui.html) |
| OpenAPI JSON | [Voir le document OpenAPI](https://sport-club-management-production-6f45.up.railway.app/api-docs) |

> **À propos des données :** la configuration actuelle utilise une base H2 en mémoire. Les données peuvent donc être réinitialisées lorsque l’application redémarre. Pour une utilisation durable en production, configurez une base de données persistante.

## 🧰 Technologies

| Technologie | Utilisation |
|---|---|
| Java 17 | Langage de développement |
| Spring Boot 3.2.3 | Framework applicatif |
| Spring Web / Spring MVC | Pages web et endpoints REST |
| Spring Data JPA | Accès aux données |
| Thymeleaf | Rendu des pages HTML côté serveur |
| H2 Database | Base de données par défaut |
| Springdoc OpenAPI / Swagger UI | Documentation de l’API |
| Maven | Gestion des dépendances et compilation |
| JUnit / Spring Boot Test | Tests automatisés |
| Docker | Conteneurisation |
| GitHub Actions | Workflows CI présents dans le dépôt |
| JaCoCo / SonarCloud | Configuration de couverture et d’analyse du code |

## 🏗️ Architecture du projet

L’application suit une architecture Spring Boot en couches :

```text
sport-club-management/
├── .github/
│   └── workflows/             # Workflows GitHub Actions
├── src/
│   ├── main/
│   │   ├── java/com/sportclub/
│   │   │   ├── controller/    # Contrôleurs MVC et REST
│   │   │   ├── model/         # Entités JPA
│   │   │   ├── repository/    # Accès aux données
│   │   │   └── service/       # Logique métier
│   │   └── resources/
│   │       ├── templates/     # Pages Thymeleaf
│   │       └── application.properties
│   └── test/                  # Tests automatisés
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

### Pages web

Les routes web disponibles sont :

| Route | Description |
|---|---|
| `/` | Accueil |
| `/members` | Membres |
| `/about` | À propos |
| `/activities` | Activités sportives |
| `/contact` | Contact |

## 🔌 API REST des membres

La base de l’API est :

```text
https://sport-club-management-production-6f45.up.railway.app/api/members
```

| Méthode | Endpoint | Description |
|---|---|---|
| `GET` | `/api/members` | Liste tous les membres |
| `GET` | `/api/members/{id}` | Récupère un membre par son identifiant |
| `POST` | `/api/members` | Crée un membre |
| `PUT` | `/api/members/{id}` | Modifie un membre |
| `DELETE` | `/api/members/{id}` | Supprime un membre |
| `GET` | `/api/members/search?name={nom}` | Recherche par nom |
| `GET` | `/api/members/count` | Retourne le nombre de membres |

### Exemple : lister les membres

```bash
curl https://sport-club-management-production-6f45.up.railway.app/api/members
```

### Exemple : créer un membre

Le corps JSON doit correspondre aux champs de l’entité `Member`. Exemple indicatif :

```json
{
  "firstName": "Amine",
  "lastName": "Ben Salah",
  "email": "amine@example.com",
  "phone": "+216 00 000 000",
  "address": "Tunis"
}
```

Exemple de requête :

```bash
curl -X POST \
  "https://sport-club-management-production-6f45.up.railway.app/api/members" \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Amine","lastName":"Ben Salah","email":"amine@example.com","phone":"+216 00 000 000","address":"Tunis"}'
```

Les valeurs de cet exemple sont fictives. Pour consulter le schéma complet et essayer les endpoints, utilisez Swagger UI.

## 🚀 Installation et lancement en local

### Prérequis

- JDK 17
- Git
- Maven, ou le Maven Wrapper inclus dans le dépôt
- Docker (facultatif, pour lancer l’application en conteneur)

### 1. Cloner le dépôt

```bash
git clone https://github.com/WissemAttouf/sport-club-management.git
cd sport-club-management
```

### 2. Démarrer avec Maven

**Windows PowerShell :**

```powershell
.\mvnw.cmd spring-boot:run
```

**Linux / macOS :**

```bash
./mvnw spring-boot:run
```

L’application utilise par défaut le port `8080` (ou la valeur de la variable d’environnement `PORT`). Ouvrez ensuite :

- Site : [http://localhost:8080](http://localhost:8080)
- API : [http://localhost:8080/api/members](http://localhost:8080/api/members)
- Swagger UI : [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI JSON : [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

### 3. Exécuter les tests

**Windows PowerShell :**

```powershell
.\mvnw.cmd test
```

**Linux / macOS :**

```bash
./mvnw test
```

## 🐳 Lancer avec Docker

Depuis la racine du dépôt :

### Construire l’image

```bash
docker build -t sport-club-management .
```

### Démarrer le conteneur

```bash
docker run --rm -p 8080:8080 -e PORT=8080 sport-club-management
```

Puis ouvrez [http://localhost:8080](http://localhost:8080).

Le Dockerfile utilise une étape Maven avec Java 17 pour construire le JAR, puis une image d’exécution Java 17 plus légère. La commande de build actuelle ignore l’exécution des tests ; lancez les tests séparément avant une livraison.

## ☁️ Déploiement

### Backend — Railway

Le backend est déployé sur Railway :

```text
https://sport-club-management-production-6f45.up.railway.app
```

Le domaine public peut être utilisé pour tester les pages et l’API. La disponibilité effective de chaque route dépend de l’état du déploiement.

### Frontend séparé — Vercel

Le code actuel contient des pages Thymeleaf servies par Spring Boot. Si un frontend statique séparé est ajouté au dépôt, configurez Vercel pour utiliser son dossier comme **Root Directory** et faites appeler l’API Railway depuis ce frontend. Le dossier frontend séparé et sa configuration Vercel doivent être présents dans le dépôt avant de configurer ce déploiement.

Si le frontend appelle l’API depuis un autre domaine, configurez également CORS côté Spring Boot pour autoriser uniquement les origines nécessaires.

## 🖼️ Captures d’écran

Les captures réelles de l’application ne sont pas intégrées à ce dépôt pour le moment. Pour illustrer le README, ajoutez des images prises depuis l’application dans `docs/screenshots/`, par exemple :

- `home.png` — page d’accueil
- `members.png` — page des membres
- `swagger.png` — documentation de l’API

Une fois les fichiers ajoutés, affichez-les avec Markdown, par exemple :

```markdown
![Page d’accueil](docs/screenshots/home.png)
```

Utilisez de véritables captures du projet afin que la documentation reflète l’interface réellement déployée.

## 🔐 Points à vérifier avant une utilisation en production

- Remplacer H2 en mémoire par une base persistante si les données doivent survivre aux redémarrages.
- Désactiver ou sécuriser la console H2 avant d’exposer l’application publiquement.
- Vérifier les règles CORS si un frontend séparé est déployé.
- Externaliser les identifiants et secrets dans des variables d’environnement.
- Vérifier les ports, profils Spring et paramètres de base de données pour chaque environnement.
- Exécuter les tests et valider les workflows Docker, CI/CD, Kubernetes et monitoring avant de s’appuyer sur eux en production.

## 🤝 Contribution

Les suggestions et signalements de bugs sont les bienvenus. Pour proposer une amélioration :

1. Créez une branche dédiée.
2. Décrivez clairement le changement.
3. Ajoutez ou adaptez les tests lorsque c’est pertinent.
4. Ouvrez une Pull Request avec les étapes de vérification.

## 📄 Licence

Aucun fichier de licence n’est actuellement déclaré dans le dépôt. En l’absence de licence, les droits de réutilisation et de redistribution ne doivent pas être présumés.

---

Développé avec **Java, Spring Boot et Docker**.
