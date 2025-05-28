## Présentation de RCM'ss
Composé de :
- Cyrille Dos Ghali
- Steeve Leuba
- Saverio Martini
- Melissa Mandeleu
- Ryan Vermeille

Le groupe RMC'ss a pour but de reprendre le code legacy d'une application de gestion d'allocations familiales pour la ville de Lausanne. Cette application permettra aux familles de Lausanne de gérer leurs allocations familiales de manière simple et efficace. Elle permettra également aux employés de la ville de Lausanne de gérer les demandes d'allocations familiales de manière simple et efficace.

Le pair programming et l'agilité font partie des fondamentaux de notre groupe.
## Initialisation du projet

- Dépôt initialisé sur **CyriGIT** en mode **public** (https://github.com/CyriGIT/Project_PratiqueDeveloppement.git)
- Dépendances, configuration de la **base de données (DDL + DML)** en place
- Collaborateurs ajoutés
- Création de la branche `dev` et d'une branche par exercice
- Mise en place des tests (unitaires & intégration)
- Gestion centralisée des logs via Log4j2

---

## Structure du projet simplifiée

```text
src/
├── main/
│   ├── java/
│   │   └── ch.hearc.cafheg/
│   │       ├── business/         → logique métier (services, entités)
│   │       ├── infrastructure/   → REST API, persistence, PDF, openAPI
│   │       └── utils/            → outils transversaux (logger)
│   └── resources/                → configuration & logs
│
├── test/
│   └── java/                     → tests unitaires
│
└── integration-test/
    ├── java/                     → tests d’intégration
    └── resources/                → jeux de données DBUnit 
```

---

## Exercice 1 — Prise en main & refactoring

- Test du **endpoint REST** : `/droits/quel-parent` avec Postman (ou équivalent)
- Couverture de test à 100% pour `AllocationService#getParentDroitAllocation`
- **Refactoring TDD** : remplacement de `Map<String, Object>` par une classe métier
- Vérification de la non-régression via tests REST

Ce premier exercice a servi de mise en bouche dans le début de ce projet, aucune difficulté particulière n'a été rencontrée à cette étape.

L'étape concernant le refactoring de la méthode "getParentDroitAllocation" a été un peu plus délicate mais gérée avec succès par l'équipe

---

## Exercice 2 — Gestion des allocataires

- **Suppression d’un allocataire** (seulement s’il n’a aucun versement)
- **Modification** du **nom/prénom** d’un allocataire (le numéro AVS reste inchangé)
- Exposition des services via **API REST**
- Tests réalisés sur ces fonctionnalités

Les deux méthodes d'ajout et de suppression n'ont pas posé de difficutés à être implémentées. Le challenge à résider dans l'exposition des services avec le framework SpringBoot, technologie que nous n'avons encore jamais utilisé. Après plusieurs essais infructueux, le résultat a finalement été au rendez-vous.

---

## Exercice 4 — Gestion des logs

Mise en place d’un système de **journalisation centralisé** avec **Log4j2** :

### 🔧 Appenders configurés :

- **Console (`Console`)**
  - Affiche tous les logs de niveau `debug` ou supérieur dans la console (dev).
  - Format : `[Heure] [Thread] [Niveau] [Classe] - message`.

- **Fichier d’erreurs (`ErrorFile`)**
  - Fichier : `logs/err.log`
  - Enregistre uniquement les messages `error` issus des packages contenant (`"ch"`).
  - Format lisible avec timestamp précis.

- **Fichier journalier pour les services (`ServiceFile`)**
  - Fichier : `logs/cafheg_yyyy-MM-dd.log` (journal rotatif quotidien)
  - Niveau `info` uniquement
  - Ciblé sur le package `ch.hearc.cafheg.business` (services métier)
  - Rotation journalière + rotation en taille (10 MB)

---

## Exercice 5 — Tests d’intégration (DBUnit)

- Nouvelle arborescence `integration-test/` pour séparer les tests
- Ajout de tests d’intégration pour :
  - **Suppression d’un allocataire**
  - **Modification d’un allocataire**
- Utilisation de **DBUnit**, **JUnit 5**, **AssertJ**
- Fichiers de données : `allocataire.xml`, `versement.xml`, `schema.sql`

---

## Exemple : Tester l'endpoint `/droits/quel-parent`

Utiliser **POSTMAN** :
- Méthode : `POST`
- URL : `http://localhost:8080/droits/quel-parent`
- Body → `raw`, format JSON :

```json
{
  "enfantResidence": "CH",
  "parent1ActiviteLucrative": true,
  "parent1Residence": "CH",
  "parent2ActiviteLucrative": false,
  "parent2Residence": "CH",
  "parentsEnsemble": true,
  "parent1Salaire": 6000,
  "parent2Salaire": 4500
}
```

---

## Technologies utilisées

- Java 11 (Corretto)
- Spring Boot
- JUnit 5
- DBUnit
- Log4j2
- OpenAPI (Swagger)

---