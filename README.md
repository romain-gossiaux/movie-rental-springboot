# Movie Rental API

## Sujet

Ce projet est une **API REST de location de films** développée avec **Spring Boot**.  
Il permet de gérer un catalogue de films, leurs catégories, les utilisateurs et les locations de films, avec une gestion des rôles et des règles métier.

## Modèle de données

L’application est basée sur les entités suivantes :

### Entités
- **Movie**
  - id
  - title
  - director
  - releaseYear
  - imdbId (unique)
  - pricePerDay
  - category

- **Category**
  - id
  - name
  - relation : une catégorie possède plusieurs films (`OneToMany`)

- **User**
  - id
  - username (unique)
  - password
  - roles (`ManyToMany`)

- **Role**
  - id
  - name (ex: ROLE_USER, ROLE_ADMIN)

- **Rental**
  - id
  - user
  - movie
  - startDate
  - durationDays
  - totalPrice

### Relations principales
- Un **Movie** appartient à une **Category** (`ManyToOne`)
- Un **User** peut avoir plusieurs **Role** (`ManyToMany`)
- Une **Rental** est liée à un **User** et à un **Movie** (`ManyToOne`)
- Règle métier : un utilisateur ne peut pas louer deux fois le même film en même temps

## Fonctionnalités

- CRUD complet sur :
  - Movie
  - Category
  - Rental
- Validation des entrées
- Calcul automatique du prix total d’une location
- Sécurité Spring Security :
  - `USER` : accès en lecture
  - `ADMIN` : création, modification et suppression
- Base de données PostgreSQL via Docker
- Des données initiales (rôles et utilisateurs) sont automatiquement créées au démarrage de l’application

## Lancer le projet

### Prérequis
- Java 17+
- Maven
- Docker & Docker Compose

### Étapes

1. Cloner le repository
```bash
git clone <url-du-repo>
cd movie-rental-api
```

2. Lancer PostgreSQL avec Docker
```bash
docker-compose up -d
```
3. Lancer l'application avec Spring Boot
```bash
   ./mvnw spring-boot:run
```
L'API est accessible sur:
  http://localhost:9090
