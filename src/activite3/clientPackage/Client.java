package activite3.clientPackage;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Activite 1-3 - Etape 1 et 2 : Client de calculatrice ameliore
 *
 * Etape 1 : Utilisation de InetAddress / InetSocketAddress pour se connecter
 *           a l'adresse IP reelle du serveur (remplace localhost).
 *           Fonctionne avec le serveur sur une machine differente du reseau local.
 *
 * Etape 2 : Presente un menu a 4 choix d'operation :
 *           1 -> Addition        (x + 5)
 *           2 -> Soustraction    (x - 5)
 *           3 -> Multiplication  (x * 5)
 *           4 -> Division        (x / 5)
 *           0 -> Quitter
 *
 * Protocole de communication :
 *   Client -> Serveur : int choix (1-4, ou 0 pour quitter)
 *   Client -> Serveur : int x  (uniquement si choix != 0)
 *   Serveur -> Client : int resultat  (uniquement si choix != 0)
 */
public class Client {

    // ---------------------------------------------------------------
    // Adresse IP du serveur --- MEME VALEUR que dans Server.java
    // ---------------------------------------------------------------
    private static final String SERVER_IP  = "10.110.130.33";
    private static final int    SERVER_PORT = 1234;

    public static void main(String[] args) {
        try {
            System.out.println("=== Client de calculatrice (Activite 1-3) ===");
            System.out.println("Je suis un client pas encore connecte...");

            // --- Etape 1 : InetAddress + InetSocketAddress ---
            InetAddress inetAddress = InetAddress.getByName(SERVER_IP);
            InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, SERVER_PORT);

            Socket socket = new Socket();
            socket.connect(socketAddress);

            System.out.println("Je suis un client connecte au serveur : "
                    + SERVER_IP + ":" + SERVER_PORT);

            // Flux de donnees typees
            Scanner          scanner = new Scanner(System.in);
            DataOutputStream dos     = new DataOutputStream(socket.getOutputStream());
            DataInputStream  dis     = new DataInputStream(socket.getInputStream());

            // --- Etape 2 : Menu de la calculatrice ---
            while (true) {
                System.out.println("\n--- Menu de la calculatrice ---");
                System.out.println("1. Addition        (x + 5)");
                System.out.println("2. Soustraction    (x - 5)");
                System.out.println("3. Multiplication  (x * 5)");
                System.out.println("4. Division        (x / 5)");
                System.out.println("0. Quitter");
                System.out.print("Votre choix : ");
                int choix = scanner.nextInt();

                // Envoi du choix au serveur
                dos.writeInt(choix);

                if (choix == 0) {
                    System.out.println("Deconnexion en cours...");
                    break;
                }

                if (choix < 1 || choix > 4) {
                    System.out.println("Choix invalide. Veuillez entrer un nombre entre 0 et 4.");
                    continue;
                }

                // Saisie et envoi de la valeur x
                System.out.print("Veuillez entrer un entier x : ");
                int x = scanner.nextInt();
                dos.writeInt(x);

                // Reception et affichage du resultat
                int resultat = dis.readInt();
                String[] operations = {"", "Addition", "Soustraction", "Multiplication", "Division"};
                System.out.println("Resultat [" + operations[choix] + "] : " + resultat);
            }

            // Fermeture des ressources
            socket.close();
            scanner.close();
            System.out.println("Connexion fermee. Au revoir !");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}