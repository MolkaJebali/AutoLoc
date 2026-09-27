# 🚗 AutoLoc API — Plateforme de Gestion de Location de Véhicules

[![Java](https://img.shields.io/badge/Java-17%2B-orange.svg?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3%20%2F%204-brightgreen.svg?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Data JPA](https://img.shields.io/badge/Spring%20Data-JPA-blue.svg?logo=spring&logoColor=white)](https://spring.io/projects/spring-data-jpa)
[![Hibernate](https://img.shields.io/badge/ORM-Hibernate-59666C.svg?logo=hibernate&logoColor=white)](https://hibernate.org/)
[![MySQL](https://img.shields.io/badge/Database-MySQL%20%2F%20MariaDB-4479A1.svg?logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Build-Maven%20Wrapper-C71A36.svg?logo=apachemaven&logoColor=white)](https://maven.apache.org/)

---

## 🎓 Cadre Académique & Présentation de la Matière

* **Établissement** : [ESPRIT](https://esprit.tn/) (École Supérieure Privée d'Ingénierie et de Technologies)
* **Unité Pédagogique** : **UP ASI** — Architecture des Systèmes d'Information
* **Niveau** : 4ᵉ Année Ingénieur Informatique (Année 26-27)
* **Étudiante** : **Molka Jebali**
* **Module** : **Architecture des Systèmes d'Information (ASI)**

### Objectifs d'Apprentissage des Ateliers 1 & 2
Le module d'**Architecture des Systèmes d'Information** vise à doter les futurs ingénieurs des compétences requises pour concevoir des architectures applicatives robustes, évolutives et modulaires en environnement d'entreprise. 

Dans ce cadre, les **Ateliers 1 & 2** posent le socle fondamental du projet fil rouge **AutoLoc** :
1. **Initialisation technique** : Démarrage d'une application Spring Boot et structuration d'un projet d'entreprise avec Maven.
2. **Couche de Persistance & JPA** : Configuration de la connectivité avec MySQL, gestion du cycle de vie des entités et configuration de la génération DDL d'Hibernate (`ddl-auto=update`).
3. **Clean Code & Bonnes Pratiques** :
   * Utilisation ciblée des annotations Lombok (`@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`) en proscrivant `@Data` sur les entités de domaine afin d'anticiper les pièges de récursivité infinie (`equals`/`hashCode`/`toString`) lors de l'intégration des futures relations bidirectionnelles.
   * Définition rigoureuse des contraintes de schéma (unicité, nullabilité, précisions numériques, typages énumérés stricts).
4. **Modélisation de Domaine** : Mise en place complète des 9 entités de domaine du système d'information de location automobile avant l'étape de modélisation des associations complexes (relations, cascade, fetch de l'Atelier 2).

---

## 📌 Présentation du Projet : AutoLoc

**AutoLoc** est une solution logicielle d'architecture orientée services (API REST & Persistance JPA) dédiée à la numérisation complète de l'activité d'une agence moderne de location de véhicules :
* Gestion de flotte de véhicules (statut de disponibilité, tarification journalière, catégorisation).
* Gestion des clients et des permis de conduire.
* Suivi des réservations et validation des contrats.
* Gestion des paiements (modes carte, espèces, virement).
* Planification et traçabilité des opérations de maintenance de la flotte.
* Administration du personnel (agents et managers) et des agences réparties sur le réseau.

---

## 🏗️ Architecture et Arborescence du Projet

Le projet suit une organisation en couches logiques (*Layered Architecture*) conforme aux standards Spring d'entreprise :

```text
tn.esprit.autoloc
├── domain                     # Entités JPA du modèle de domaine & Énumérations
│   ├── Agence.java            # Entité Agence
│   ├── Client.java            # Entité Client (avec contraintes d'unicité email & permis)
│   ├── Contrat.java           # Entité Contrat de location
│   ├── Employe.java           # Entité Employé
│   ├── Equipement.java        # Entité Équipement optionnel
│   ├── Maintenance.java       # Entité Maintenance technique
│   ├── Paiement.java          # Entité Transaction de paiement
│   ├── Reservation.java       # Entité Réservation de véhicule
│   ├── Vehicule.java          # Entité Véhicule (première entité socle)
│   │
│   ├── CategorieVehicule.java # Enum (CITADINE, BERLINE, SUV, UTILITAIRE)
│   ├── ModePaiement.java      # Enum (CARTE, ESPECES, VIREMENT)
│   ├── RoleEmploye.java       # Enum (AGENT, MANAGER)
│   ├── StatutReservation.java # Enum (EN_ATTENTE, CONFIRMEE, ANNULEE, TERMINEE)
│   └── StatutVehicule.java    # Enum (DISPONIBLE, LOUE, MAINTENANCE)
│
├── repository                 # Couche d'accès aux données (Spring Data JPA)
│   └── VehiculeRepository.java# Interface d'accès aux entités Véhicule
│
├── service                    # Couche Métier (logique applicative - Ateliers ultérieurs)
├── web.controller             # Contrôleurs REST (exposition des endpoints - Ateliers ultérieurs)
├── web.dto                    # Data Transfer Objects (transfert et découplage API)
│
├── AutolocApiApplication.java # Classe principale d'amorçage Spring Boot
└── DataInitializer.java       # CommandLineRunner d'insertion des données de démonstration
```

---

## 📊 Modèle de Données & Entités Réalisées

Conformément au sujet de l'Atelier 1 et du travail préparatoire de l'Atelier 2, l'ensemble des **9 entités** a été créé dans le package `domain` sans associations préalables (afin de construire les relations collectivement en séance 3) :

| # | Entité | Clé Primaire (`@Id`) | Attributs & Types | Énumérations associées |
|---|--------|---------------------|-------------------|------------------------|
| **1** | **Vehicule** | `idVehicule` (Long, Auto) | `immatriculation` (unique), `marque`, `modele`, `tarifJournalier` (BigDecimal 10,2) | `CategorieVehicule`, `StatutVehicule` |
| **2** | **Agence** | `idAgence` (Long, Auto) | `nom`, `ville`, `adresse`, `telephone` | — |
| **3** | **Client** | `idClient` (Long, Auto) | `nom`, `prenom`, `email` (unique), `telephone`, `numPermis` (unique), `dateInscription` (LocalDate) | — |
| **4** | **Employe** | `idEmploye` (Long, Auto) | `nom`, `prenom` | `RoleEmploye` (`AGENT`, `MANAGER`) |
| **5** | **Equipement** | `idEquipement` (Long, Auto) | `libelle` | — |
| **6** | **Reservation** | `idReservation` (Long, Auto) | `dateDebut` (LocalDate), `dateFin` (LocalDate) | `StatutReservation` (`EN_ATTENTE`, `CONFIRMEE`, `ANNULEE`, `TERMINEE`) |
| **7** | **Contrat** | `idContrat` (Long, Auto) | `dateSignature` (LocalDate), `montantTotal` (BigDecimal), `valide` (Boolean) | — |
| **8** | **Paiement** | `idPaiement` (Long, Auto) | `montant` (BigDecimal), `datePaiement` (LocalDate) | `ModePaiement` (`CARTE`, `ESPECES`, `VIREMENT`) |
| **9** | **Maintenance** | `idMaintenance` (Long, Auto) | `dateDebut` (LocalDate), `dateFin` (LocalDate), `description` (String) | — |

---

## ⚙️ Configuration & Environnement

### 1. Fichier `application.properties`
* **Base de données** : MySQL / MariaDB sur le port `3306`.
* **Création automatique de la base** : `createDatabaseIfNotExist=true` inclus dans l'URL JDBC (`autoloc_db`).
* **Sécurité du mot de passe** : paramétré via variable d'environnement avec valeur de repli locale (`${DB_PASSWORD:}`).
* **Port d'écoute** : `8081` par défaut (`${PORT:8081}`) pour garantir l'absence de conflit d'affectation réseau avec d'autres services locaux (ex: Oracle TNS Listener sur 8080).
* **Génération automatique du schéma** : `spring.jpa.hibernate.ddl-auto=update`.
* **Traces SQL** : logs au niveau `DEBUG` pour observer le requêtage DDL & DML émis par Hibernate.

### 2. Profil de Développement (`application-dev.properties`) *(Fonctionnalité Bonus)*
Un profil d'environnement distinct `dev` est mis à disposition pour un niveau de verbosité accru et une flexibilité de test.

---

## 🚀 Insertion Automatique de Données de Démonstration *(Fonctionnalité Bonus)*

Un composant **`DataInitializer`** implémentant `CommandLineRunner` est intégré. Au démarrage, il vérifie l'état de la table `vehicule` et injecte automatiquement 3 véhicules de test si la base est vierge :
* 🚗 **Renault Clio 5** (`234-TN-5678`) — Catégorie : *CITADINE*, Tarif : 90.00 DT/jour, Statut : *DISPONIBLE*.
* 🚙 **Peugeot 3008** (`235-TN-1234`) — Catégorie : *SUV*, Tarif : 160.00 DT/jour, Statut : *DISPONIBLE*.
* 🚘 **Volkswagen Passat** (`236-TN-9876`) — Catégorie : *BERLINE*, Tarif : 190.00 DT/jour, Statut : *MAINTENANCE*.

---

## 🛠️ Instructions d'Exécution & Démarrage

### Prérequis
* **JDK 17+** installé et configuré (`java -version`).
* **SGBD MySQL / MariaDB** (via XAMPP, WAMP ou service MySQL dédié) en cours d'exécution sur le port `3306`.

### Lancer l'Application
Cloner le dépôt et exécuter à la racine :

```bash
# Sous Windows (Invite de commandes ou PowerShell)
.\mvnw.cmd spring-boot:run

# Sous Linux / macOS
./mvnw spring-boot:run
```

### Compiler et empaqueter le JAR exécutable :
```bash
.\mvnw.cmd clean package -DskipTests
java -jar target/autoloc-api-0.0.1-SNAPSHOT.jar
```

---

## 📜 Historique Git & Conformité aux Livrables

Le dépôt est versionné et synchronisé sur GitHub conformément aux étapes du guide de l'atelier :
* **Commit 1** : `Atelier 1 : init projet Spring Boot + entite Vehicule`
* **Commit 2** : `Prepa Atelier 2 : entites restantes sans associations`
