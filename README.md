# TP1 : Les sockets en mode connecté TCP

**Matière :** Développement d’applications réparties
**Activité 1-1 :** Première connexion Client/Serveur

---

### Étape 1 : Préparation de l’environnement Git

**Commande exécutée :**
```powershell
git --version
```
**Capture d'écran (Résultat Console) :**
```text
git version 2.x.x.windows.x
```
*(Remarque : Git a bien été initialisé avec `git init` dans le répertoire TP1)*

---

### Étape 2 : Préparation de l’environnement Java

**Commande exécutée :**
```powershell
java --version
```
**Capture d'écran (Résultat Console) :**
```text
java 25.0.4.1 2026-08-18 LTS
Java(TM) SE Runtime Environment (build 25.0.4.1+1-LTS-5)
Java HotSpot(TM) 64-Bit Server VM (build 25.0.4.1+1-LTS-5, mixed mode, sharing)
```

---

### Étape 3 : Création du serveur

Nous avons créé le package `serverPackage` contenant la classe `Server.java`.

**Lancement du serveur :**
```powershell
java -cp src serverPackage.Server
```

**Capture d'écran :**
![Capture Serveur](captures/capture1-serveur%20en%20aatend.png)

**Interprétation :**
Le serveur s'est lancé avec succès. Il s'est mis en écoute sur le port 1234 (`ServerSocket.accept()`). Dès que l'application cliente s'est connectée, le serveur a accepté la connexion et a affiché "un client est connecté" avant de fermer les sockets.

---

### Étape 4 : Création du client

Nous avons créé le package `clientPackage` contenant la classe `Client.java`.

**1. Test avec `localhost` :**
Le client se connecte d'abord au serveur en utilisant `localhost`.

**Capture d'écran du client avec localhost :**
![Capture Client Localhost](captures/cap2-client%20avec%20localhost.png)

**Interprétation :**
Le client démarre, affiche son message initial "Je suis un client pas encore connecté...", puis établit la connexion TCP (`new Socket("localhost", 1234)`) avec le serveur. Une fois la connexion réussie, il affiche le message confirmant sa connexion.

**2. Test avec l'adresse IP (Étape 4.f) :**
Le code a été modifié pour utiliser l'adresse IP `127.0.0.1` à la place de `localhost`.

**Capture d'écran du client avec adresse IP :**
![Capture Client IP](captures/cap2-client%20avec%20ip.png)

---

### Étape 5 : Dépôt des livrables

**Lien du repository (ENT) :**
[https://github.com/zikosalmen/SalmenZekri_LSI3_DevAppReparties.git](https://github.com/zikosalmen/SalmenZekri_LSI3_DevAppReparties.git)
