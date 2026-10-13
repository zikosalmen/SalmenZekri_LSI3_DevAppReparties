package activite3.serverPackage;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Activite 1-3 - Etape 1 et 2 : Serveur de calculatrice ameliore
 *
 * Etape 1 : Utilisation de InetAddress / InetSocketAddress pour ecouter
 *           sur l'adresse IP reelle de la machine.
 *           Permet une connexion depuis une autre machine du reseau local.
 *
 * Etape 2 : Service de calculatrice a 4 operations :
 *           1 -> Addition        (x + 5)
 *           2 -> Soustraction    (x - 5)
 *           3 -> Multiplication  (x * 5)
 *           4 -> Division        (x / 5)
 *
 * Protocole de communication :
 *   Client -> Serveur : int choix (1-4, ou 0 pour quitter)
 *   Client -> Serveur : int x  (uniquement si choix != 0)
 *   Serveur -> Client : int resultat  (uniquement si choix != 0)
 */
public class Server {

    // ---------------------------------------------------------------
    // Adresse IP et port d'ecoute --- MEME VALEUR cote Client
    // Remplacez par l'adresse IP reelle de la machine serveur.
    // ---------------------------------------------------------------
    private static final String SERVER_IP  = "10.110.130.33";
    private static final int    SERVER_PORT = 1234;

    public static void main(String[] args) {
        try {
            // --- Etape 1 : InetAddress + InetSocketAddress ---
            InetAddress inetAddress = InetAddress.getByName(SERVER_IP);
            InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, SERVER_PORT);

            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(socketAddress);

            System.out.println("=== Serveur de calculatrice (Activite 1-3) ===");
            System.out.println("Adresse IP  : " + SERVER_IP);
            System.out.println("Port        : " + SERVER_PORT);
            System.out.println("En attente de connexion d'un client...");

            Socket socket = serverSocket.accept();

            System.out.println("Un client est connecte depuis : "
                    + socket.getInetAddress().getHostAddress()
                    + ":" + socket.getPort());

            // Flux de donnees typees
            DataInputStream  dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // --- Etape 2 : Boucle de traitement des requetes ---
            while (true) {
                // Reception du choix d'operation
                int choix = dis.readInt();

                if (choix == 0) {
                    System.out.println("Le client a demande la deconnexion (choix = 0).");
                    break;
                }

                // Reception de la valeur x
                int x = dis.readInt();
                System.out.println("Operation recue : choix=" + choix + ", x=" + x);

                int resultat = 0;
                String operationStr = "";

                switch (choix) {
                    case 1: // Addition
                        resultat = x + 5;
                        operationStr = x + " + 5 = " + resultat;
                        break;
                    case 2: // Soustraction
                        resultat = x - 5;
                        operationStr = x + " - 5 = " + resultat;
                        break;
                    case 3: // Multiplication
                        resultat = x * 5;
                        operationStr = x + " * 5 = " + resultat;
                        break;
                    case 4: // Division (entiere)
                        resultat = x / 5;
                        operationStr = x + " / 5 = " + resultat;
                        break;
                    default:
                        System.out.println("Operation inconnue : " + choix);
                        resultat = -1;
                        operationStr = "Operation invalide";
                        break;
                }

                System.out.println("Resultat calcule : " + operationStr);

                // Envoi du resultat au client
                dos.writeInt(resultat);
                System.out.println("Resultat envoye au client.");
            }

            // Fermeture des ressources
            socket.close();
            serverSocket.close();
            System.out.println("Serveur ferme proprement.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}