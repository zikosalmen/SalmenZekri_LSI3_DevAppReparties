package clientPackage;

import java.net.Socket;
import java.io.IOException;

public class Client {
    public static void main(String[] args) {
        try {
            // La première étape :
            System.out.println("Je suis un client pas encore connecté...");
            
            // Connexion au serveur (TODO)
            Socket socket = new Socket("127.0.0.1", 1234);
            
            System.out.println("je suis un client connecté");
            
            // La dernière étape : Fermer socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
