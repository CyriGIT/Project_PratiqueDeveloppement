# Rapport
## Présentation de RCM'ss
Composé de :
- Cyrille Dos Ghali
- Steeve Leuba
- Saverio Martini
- Melissa Mandeleu
- Ryan Vermeille

Le groupe RMC'ss a pour but de créer une application de gestion d'allocations familiales pour la ville de Lausanne. Cette application permettra aux familles de Lausanne de gérer leurs allocations familiales de manière simple et efficace. Elle permettra également aux employés de la ville de Lausanne de gérer les demandes d'allocations familiales de manière simple et efficace.
Le pair programming et l'agilité font partie des fondamentaux de notre groupe.
## Initialisation du projet
- projet configuré avec les dépendances et les ressources ( la BD avec les ddl et dml)
- repo du projet créé sur CyriGIT (en public)
    - collaborateur ajoutés
- branche dev créée (on a dit qu'on fera une branche par exercice)
## Test de l'Endpoint "/droits/quel-parent"
- pour tester http://localhost:8080/droits/quel-parent il faut :
    - utiliser un outil comme POSTMAN
    - utiliser une requête POST
    - dans body cocher RAW
    - utiliser les param
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
## Mise en place des tests