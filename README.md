# **Système de Gestion des Colis**

## **Description**
Ce projet est une simulation d'un système de gestion de livraison de colis. Il permet d'ajouter des colis avec un nom de propriétaire, de visualiser leur état dans une interface graphique et de suivre automatiquement leur progression à travers les statuts suivants :
- **En attente**
- **En transit**
- **Livré**

L'interface utilise **JavaFX** pour une expérience utilisateur intuitive.

---

## **Fonctionnalités**
- Ajouter un colis avec un nom de propriétaire.
- Génération automatique d’un ID pour chaque colis ajouté.
- Affichage des colis dans une table interactive avec les colonnes :
  - **ID Colis** : Identifiant unique généré automatiquement.
  - **Propriétaire** : Nom du propriétaire du colis.
  - **Statut** : État du colis (`En attente`, `En transit`, `Livré`).
- Simulation automatique de la livraison :
  - Les colis passent de `En attente` à `En transit`, puis à `Livré`.
  - L'état des colis est mis à jour en temps réel.
- Affichage d'un message de succès après l'ajout d'un colis.

---

## **Technologies Utilisées**
- **Langage** : Java
- **Interface graphique** : JavaFX
- **Gestion des threads** : Classes Java concurrentes (e.g., `ExecutorService`, `ScheduledExecutorService`)

---

## **Installation et Exécution**
### **Prérequis**
1. **Java JDK 11 ou supérieur** : Assurez-vous que Java est installé sur votre machine.
2. **JavaFX SDK** : Téléchargez et configurez JavaFX si nécessaire.

### **Étapes d'exécution**
1. Clonez le projet :
   ```bash
   git clone https://github.com/WYMma/LivColis.git
   cd SystemeGestionColis
   ```
2. Compilez le projet en ajoutant les fichiers JavaFX à votre classpath :
   ```bash
   javac --module-path /path/to/javafx/lib --add-modules javafx.controls -d out *.java
   ```
3. Exécutez le projet :
   ```bash
   java --module-path /path/to/javafx/lib --add-modules javafx.controls -cp out ApplicationColis
   ```

---

## **Utilisation**
1. **Ajouter un colis** :
   - Entrez un nom dans le champ de texte "Nom du propriétaire".
   - Appuyez sur **Entrée** ou cliquez sur le bouton "Ajouter le colis".
   - Un message en vert s'affiche : *"Colis #ID ajouté avec succès!"*.
2. **Suivre les colis** :
   - Consultez le tableau pour suivre les colis et leurs statuts.
   - Les colis progressent automatiquement de `En attente` à `Livré`.
3. **Affichage dynamique** :
   - La table se met à jour automatiquement lorsque l'état d'un colis change.
