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

---
---

# Activité 1-2 : Service de calculatrice

**Matière :** Développement d'applications réparties
**Activité 1-2 :** Service de calculatrice (Client/Serveur avec flux de données typées)

---

### Étape 1 : Préparation de l'espace de travail

Nous repartons du code de l'Activité 1-1. L'objectif est d'enrichir la communication Client/Serveur pour qu'elle échange des entiers à l'aide de flux de données typées (`DataInputStream` / `DataOutputStream`), et qu'elle fonctionne en boucle jusqu'à la saisie de `0`.

---

### Étape 2 : Création du service de multiplication

**1. Extension des applications Client et Serveur :**
Nous avons étendu le code de l'Activité 1-1 pour que le client saisisse un entier, l'envoie au serveur, et que le serveur calcule le produit de cet entier par 5 avant de renvoyer le résultat au client.

**2. Lancement des applications :**

**Capture d'écran — Connexion du serveur :**
![Capture Serveur App2](captures/app2-etape2.png)

**Interprétation :**
Le serveur se lance et se met en écoute sur le port 1234. Il affiche le message d'attente, puis accepte la connexion du client.

---

### Étape 3 : Test du programme avec différentes valeurs

Lors des premiers tests avec les classes `InputStream` et `OutputStream` classiques, la transmission de nombres négatifs pose problème : la méthode `write()` ne transmet qu'un seul octet (valeurs de 0 à 255). Cela rend impossible l'envoi de valeurs négatives ou supérieures à 255 de façon correcte.

**Capture d'écran — Test initial :**
![Capture App2 Étape 3](captures/app2-etape3.png)

![Capture App2 Étape 3b](captures/app2-etape3b.png)

![Capture App2 Étape 3c](captures/app2-etape3c.png)

**Solution :** utiliser des flux de données typées.

---

### Étape 4 : Exploitation de `java.io.DataInputStream` et `java.io.DataOutputStream`

Nous avons mis à jour le code Client et Serveur pour utiliser ces deux classes. Elles disposent des méthodes `readInt()` et `writeInt()` qui permettent de transmettre des entiers sur **32 bits**, résolvant ainsi le problème des entiers négatifs.

**Code clé (Server.java) :**
```java
DataInputStream dis = new DataInputStream(socket.getInputStream());
DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

while (true) {
    int x = dis.readInt();
    if (x == 0) {
        System.out.println("Le client s'est déconnecté (0 reçu).");
        break;
    }
    int result = x * 5;
    dos.writeInt(result);
}
```

**Code clé (Client.java) :**
```java
DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
DataInputStream dis = new DataInputStream(socket.getInputStream());

while (true) {
    System.out.print("Veuillez taper un entier x (0 pour quitter) : ");
    int x = scanner.nextInt();
    dos.writeInt(x);
    if (x == 0) break;
    int result = dis.readInt();
    System.out.println("Le résultat reçu du serveur est : " + result);
}
```

**Capture d'écran — Test avec un nombre négatif (`java.io.DataOutputStream`) :**
![Capture App2 - Nombre négatif (DataOutputStream)](captures/app2-etape4.png)

**Interprétation :**
Le client saisit `-2`. Grâce à `DataOutputStream.writeInt()`, l'entier est transmis correctement sur 32 bits. Le serveur calcule `-2 × 5 = -10` et renvoie le résultat via `DataOutputStream`. Le client affiche bien `-10`, confirmant que le problème des entiers négatifs est résolu.

---

### Étape 5 : Modification de l'application — Boucle multi-valeurs

Nous avons modifié le code pour que le client puisse saisir plusieurs entiers successivement. La boucle `while (true)` ne s'arrête que si l'utilisateur saisit `0`, ce qui entraîne une déconnexion propre des deux côtés.

**Test avec un nombre positif :**
![Capture App2 - Nombre positif](captures/app2-etape5a.png)

**Interprétation :**
Le client envoie un entier positif. Le serveur calcule correctement `x × 5` et le renvoie. Ce test valide le bon fonctionnement de la boucle pour les valeurs positives.

---

**Test avec un nombre négatif (dans la boucle) :**
![Capture App2 - Nombre négatif](captures/app2-etape5b.png)

**Interprétation :**
La boucle gère correctement les entiers négatifs grâce aux flux `DataInputStream`/`DataOutputStream`. Le résultat renvoyé est toujours `x × 5`.

---

**Test de la fermeture des sockets (saisie de `0`) :**
![Capture App2 - Envoi de 0](captures/app2-etape5c.png)

**Interprétation :**
Lorsque l'utilisateur saisit `0`, le client envoie cette valeur via `dos.writeInt(0)` puis sort de la boucle et ferme la socket. Côté serveur, la réception de `0` déclenche la sortie de la boucle avec le message `"Le client s'est déconnecté (0 reçu)."`. La déconnexion est propre des deux côtés.

---

**Session complète finale :**
![Capture App2 - Session complète](captures/app2-etape5d.png)

**Interprétation :**
Cette capture illustre une session complète où plusieurs entiers (positifs et négatifs) sont envoyés avant la déconnexion avec `0`. Le serveur traite chaque requête en boucle et calcule `x × 5`.

**Résultats observés :**
| Entier envoyé (x) | Résultat reçu (x × 5) |
|:-----------------:|:---------------------:|
| 1                 | 5                     |
| 2                 | 10                    |
| 3                 | 15                    |
| -1                | -5                    |
| -6                | -30                   |
| -4                | -20                   |
| 0                 | *(déconnexion)*       |

---

### Étape 6 : Dépôt des livrables

**Lien du repository (ENT) :**
[https://github.com/zikosalmen/SalmenZekri_LSI3_DevAppReparties.git](https://github.com/zikosalmen/SalmenZekri_LSI3_DevAppReparties.git)
