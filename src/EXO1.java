//TODO: Exercice 1




Exercice 1
Partie 1 – Prise en main
•	Faites l’état des lieux. Prenez connaissance des classes du projet, leurs différentes interactions.
•	Découvrez le moyen de lancer l’application
•	Trouvez un outil qui vous permet de tester le endpoint (RESTController) de votre application. Ce peut être un plugin de votre navigateur ou un outil dédié aux tests d’API REST.
•	Testez le endpoint « /droits/quel-parent » avec votre outil (des indications concernant ses paramètres sont présents dans la classe RESTController)
Partie 2 – Ajout de tests
•	Réaliser un harnais de tests (100%) sur la classe « AllocationService#getParentDroitAllocation » afin de comprendre son fonctionnement actuel et ses défauts.
Partie 3 – TDD & Refactoring
•	Réalisez un refactoring sur AllocationService#getParentDroitAllocation afin que ce ne soit plus une Map<String, Object> mais une autre classe.
o	Remarquez qu’un refactoring sur une API publique nécessite un gros travail sur les tests. Comprenez l’importance de définir clairement les contrats des API publiques !
•	La couverture de tests de cette classe doit toujours être de 100%
•	Contrôlez que votre API REST soit toujours fonctionnelle suite à ces modifications




