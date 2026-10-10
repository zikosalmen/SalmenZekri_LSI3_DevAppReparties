package serverPackage;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Server {
    public static void main(String[] args) {
        try {
            // La première étape :
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Je suis un serveur en attente la connexion d'un client ");
            
            Socket socket = serverSocket.accept();
            
            // La deuxième étape :
            System.out.println("un client est connecté");
            
            // Préparation des flux
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            
            while (true) {
                // c) Le serveur, qui était en attente de ce nombre, le reçoit.
                int x = dis.readInt();
                if (x == 0) {
                    System.out.println("Le client s'est déconnecté (0 reçu).");
                    break;
                }
                System.out.println("Le nombre reçu du client est : " + x);
                
                // d) Le serveur calcule par exemple le produit x*5.
                int result = x * 5;
                
                // e) Le serveur envoie le résultat au client.
                dos.writeInt(result);
                System.out.println("Le résultat a été envoyé au client.");
            }
            
            // La dernière étape : Fermer socket
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
